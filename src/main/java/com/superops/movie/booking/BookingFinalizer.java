package com.superops.movie.booking;

import com.superops.movie.enums.Status;
import com.superops.movie.theatre.seat.Seats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingFinalizer {

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public boolean confirmBooking(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (!bookingOptional.isPresent())
            return false;
        Booking booking = bookingOptional.get();
        int totalSeatOfOtherBookingWithSameSeats = getSeatCountWithSameSeatFromOtherBooking(booking);

        // if confirm booking has more seats than other booking with same seats, the confirm booking chosen to be confirmed
        if (booking.getBookedSeats().size() > totalSeatOfOtherBookingWithSameSeats) {
            Set<SeatBooked> bookedSeats = booking.getBookedSeats();
            List<Seats> seats = bookedSeats.stream().map(bs -> bs.getSeat()).collect(Collectors.toList());
            markBookingWithSameSeatsAsNotAvailable(seats);
            booking.setStatus(Status.CONFIRMED);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

    public int getSeatCountWithSameSeatFromOtherBooking(Booking booking) {
        List<Seats> seats = booking.getBookedSeats().stream().map(bs -> bs.getSeat()).collect(Collectors.toList());
        List<Booking> otherBookings = bookingRepository.findByShowIdAndIdNot(booking.getShow().getId(), booking.getId());
        otherBookings = otherBookings.stream()
            .filter(b -> b.getBookedSeats().stream().anyMatch(sb -> seats.contains(sb.getSeat())))
            .collect(Collectors.toList());
        if (otherBookings.isEmpty())
            return 0;
        return otherBookings.stream().mapToInt(b -> b.getBookedSeats().size()).max().getAsInt();
    }

    @Transactional
    public void markBookingWithSameSeatsAsNotAvailable(List<Seats> seats) {
        List<Booking> bookingToMarkAsNotAvailable = bookingRepository.findBookingByBookedSeatsAndStatus(seats, Status.PENDING).stream()
            .map(b -> {
                b.setStatus(Status.SEATS_NOT_AVAILABLE);
                return b;
            }).collect(Collectors.toList());

        bookingRepository.saveAll(bookingToMarkAsNotAvailable);
    }

}
