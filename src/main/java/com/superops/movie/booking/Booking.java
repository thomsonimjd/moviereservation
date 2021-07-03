package com.superops.movie.booking;

import com.superops.movie.enums.BookingType;
import com.superops.movie.enums.PaymentStatus;
import com.superops.movie.enums.Status;
import com.superops.movie.shows.Shows;
import com.superops.movie.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_id")
    private Shows show;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Set<SeatBooked> bookedSeats;

    @Column(name = "booking_time")
    private Instant bookingTime;

    @Column(name = "booking_expire_time")
    private Instant bookingExpireTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_type")
    private BookingType bookingType = BookingType.ONLINE;


    public Booking() {
//
    }

    public Booking(UUID id, Set<SeatBooked> bookedSeats) {
        this.id = id;
        this.bookedSeats = bookedSeats;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shows getShow() {
        return show;
    }

    public void setShow(Shows shows) {
        this.show = shows;
    }

    public Instant getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Instant bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Set<SeatBooked> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Set<SeatBooked> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getBookingExpireTime() {
        return bookingExpireTime;
    }

    public void setBookingExpireTime(Instant bookingExpireTime) {
        this.bookingExpireTime = bookingExpireTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }
}
