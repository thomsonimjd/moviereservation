package com.superops.movie.theatre.seat;

import com.superops.movie.theatre.auditorium.Auditorium;
import com.superops.movie.enums.SeatRow;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "seats")
public class Seats {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "row", nullable = false)
    private SeatRow row;

    @Column(name = "number", nullable = false)
    private Integer number;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audi_id")
    private Auditorium auditorium;

    public Seats() {
    }

    public Seats(SeatRow row, Integer number) {
        this.row = row;
        this.number = number;
    }


    public void auditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Auditorium auditorium() {
        return this.auditorium;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SeatRow getRow() {
        return row;
    }

    public void setRow(SeatRow row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}

