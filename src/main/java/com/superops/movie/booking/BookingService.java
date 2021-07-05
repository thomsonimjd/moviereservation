package com.superops.movie.booking;

import com.superops.movie.enums.PaymentStatus;
import com.superops.movie.enums.Status;
import com.superops.movie.security.SecurityUtils;
import com.superops.movie.shows.Shows;
import com.superops.movie.shows.ShowsRepository;
import com.superops.movie.theatre.seat.Seats;
import com.superops.movie.theatre.seat.SeatsRepository;
import com.superops.movie.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingFinalizer bookingFinalizer;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private ShowsRepository showsRepository;

    public Booking newBooking(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setStatus(Status.PENDING);
        booking.setPaymentStatus(PaymentStatus.UNPAID);
        booking.setBookingTime(Instant.now());
        booking.setBookingExpireTime(booking.getBookingTime().plus(2, ChronoUnit.MINUTES));
        booking.setBookedSeats(setSeatsByRowAndNumber(dto));
        booking.setShow(new Shows(dto.getShowId()));
        booking.setUser(new User(SecurityUtils.getUserId()));
        return bookingRepository.save(booking);
    }

    private Set<SeatBooked> setSeatsByRowAndNumber(BookingDTO dto) {
        UUID auditoriumId = showsRepository.getAuditoriumId(dto.getShowId());
        return dto.getBookedSeats().stream().map(s -> {
            Seats seats = seatsRepository.findByRowAndNumberAndAuditoriumId(s.getSeat().getRow(), s.getSeat().getNumber(), auditoriumId).get();
            s.setSeat(seats);
            return s;
        }).collect(Collectors.toSet());
    }

    public boolean checkIfSeatsNotExists(BookingDTO dto) {
        UUID auditoriumId = showsRepository.getAuditoriumId(dto.getShowId());
        return dto.getBookedSeats().stream().map(s -> s.getSeat()).anyMatch(s -> {
            return !seatsRepository.existsByRowAndNumberAndAuditoriumId(s.getRow(), s.getNumber(), auditoriumId);
        });
    }

    public boolean existsByBookingId(UUID id) {
        return bookingRepository.existsById(id);
    }

    @Transactional
    public boolean confirmBookingStatus(UUID id) {
        bookingRepository.updateBookingPaymentStatus(id, PaymentStatus.PAID);
        return bookingFinalizer.confirmBooking(id);
    }

    @Transactional
    public void deleteById(UUID id) {
        bookingRepository.deleteById(id);
    }

    public Page<Booking> getBookingHistory(UUID userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("bookingTime"));
        return bookingRepository.findByUserId(userId, pageRequest);
    }

    public List<Seats> getUsedSeatsByShowId(UUID showId) {
        return bookingRepository.findBookingsByShowIdaAndStatus(showId, Arrays.asList(Status.PENDING, Status.CONFIRMED))
            .stream().flatMap(b -> b.getBookedSeats().stream())
            .map(bs -> bs.getSeat()).collect(Collectors.toList());
    }

    public boolean validateIfSeatsAreInUse(BookingDTO dto) {
        List<Seats> seatsToBook = dto.getBookedSeats().stream().map(SeatBooked::getSeat).collect(Collectors.toList());
        return getUsedSeatsByShowId(dto.getShowId()).stream().anyMatch(s -> seatsToBook.contains(s));
    }

}
