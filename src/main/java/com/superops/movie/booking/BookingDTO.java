package com.superops.movie.booking;

import com.superops.movie.enums.BookingType;

import java.util.Set;
import java.util.UUID;

public class BookingDTO {

    private UUID id;
    private UUID showId;
    private Set<SeatBooked> bookedSeats;
    private BookingType bookingType = BookingType.ONLINE;

    public BookingDTO() {
//
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getShowId() {
        return showId;
    }

    public void setShowId(UUID showId) {
        this.showId = showId;
    }

    public Set<SeatBooked> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Set<SeatBooked> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }
}
