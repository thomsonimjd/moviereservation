package com.superops.movie.booking;

import com.superops.movie.constants.MovieConstants;
import com.superops.movie.security.SecurityUtils;
import com.superops.movie.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BookingResource {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(BookingResource.class);

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/bookings")
    public ResponseEntity createBooking(@RequestBody BookingDTO booking) {

        log.debug("REST request to save booking : {}", booking);

        if (booking.getId() != null)
            return new ResponseEntity<>("A new booking cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (booking.getBookedSeats().size() > MovieConstants.MAX_SEATS_ALLOWED)
            return new ResponseEntity<>("Maximum seats exceeds", HttpStatus.BAD_REQUEST);

        if (bookingService.checkIfSeatsNotExists(booking))
            return new ResponseEntity<>("Invalid Seats selected", HttpStatus.BAD_REQUEST);

        /*if (bookingService.validateIfSeatsAreInUse(booking))
            return new ResponseEntity<>("Selected sears are already in use", HttpStatus.BAD_REQUEST);*/

        return new ResponseEntity<>(bookingService.newBooking(booking), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/bookings/payment/{booking-id}")
    public ResponseEntity payment(@PathVariable("booking-id") UUID bookingId) {

        log.debug("REST request to process payments and confirm the booking : {}", bookingId);

        if (!bookingService.existsByBookingId(bookingId))
            return new ResponseEntity<>("Booking got expired or invalid id", HttpStatus.BAD_REQUEST);

        boolean bookingStatus = bookingService.confirmBookingStatus(bookingId);
        if(bookingStatus)
            return new ResponseEntity<>("Booking got expired or invalid id", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Booking is confirmed", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/bookings/my-history/{page}/{size}")
    public ResponseEntity getBookingHistory(@PathVariable("page") int page,
                                            @PathVariable("size") int count) {

        UUID userId = SecurityUtils.getUserId();
        log.debug("REST request to get booking history of a user : {}", userId);

        if (!userService.existsById(userId))
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);

        Page<Booking> bookingHistory = bookingService.getBookingHistory(userId, page, count);

        return new ResponseEntity<>(bookingHistory, HttpStatus.OK);
    }

    @GetMapping("/bookings/used-seats/{show-id}")
    public ResponseEntity getBookingHistory(@PathVariable("show-id") UUID showId) {
        log.debug("REST request to get used seats by show id : {}", showId);
        return new ResponseEntity<>(bookingService.getUsedSeatsByShowId(showId), HttpStatus.OK);
    }
}


