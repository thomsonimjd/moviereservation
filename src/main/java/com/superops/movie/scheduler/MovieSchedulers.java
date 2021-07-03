package com.superops.movie.scheduler;

import com.superops.movie.booking.BookingRepository;
import com.superops.movie.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class MovieSchedulers {
    private Logger log = LoggerFactory.getLogger(MovieSchedulers.class);

    @Autowired
    private BookingRepository bookingRepository;

    /*
     *   Deletes Bookings with status SEATS_NOT_AVAILABLE
     * */
    @Transactional
    @Scheduled(cron = "0 0/2 * * * ?")
    public void deleteBookingsWithSeatNotAvailableStatus() {
        log.info("Deleting Bookings seats are not available");
        bookingRepository.deleteAllByStatus(Status.SEATS_NOT_AVAILABLE);
    }

    /*
     *   release seats: delete expired bookings older than 2 min
     * */
    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    public void deleteBookingsByExpiryDate() {
        log.info("Deleting expired bookings by 2 min");
        bookingRepository.deleteAllByBookingExpireTimeBefore(Instant.now());
    }
}
