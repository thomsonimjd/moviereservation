package com.superops.movie.booking;

import com.superops.movie.enums.PaymentStatus;
import com.superops.movie.enums.Status;
import com.superops.movie.theatre.seat.Seats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID>, PagingAndSortingRepository<Booking, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Booking b SET b.paymentStatus=?2 WHERE b.id=?1")
    void updateBookingPaymentStatus(UUID id, PaymentStatus paid);

    @Query("SELECT b FROM Booking b LEFT JOIN b.bookedSeats bs LEFT JOIN bs.seat s WHERE b.status=?2 AND s IN (?1)")
    List<Booking> findBookingByBookedSeatsAndStatus(Collection<Seats> seats, Status status);

    Page<Booking> findByUserId(UUID userId, Pageable pageable);

    void deleteAllByStatus(Status status);

    @Query("SELECT new Booking(b.id, b.bookedSeats) FROM Booking b WHERE b.status IN (?2) AND b.show.id=?1")
    List<Booking> findBookingsByShowIdaAndStatus(UUID showId, List<Status> usedSeatStatus);

    List<Booking> findByShowIdAndIdNot(UUID showId, UUID id);

    void deleteAllByBookingExpireTimeBefore(Instant currentTime);
}
