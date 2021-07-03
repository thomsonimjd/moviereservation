package com.superops.movie.shows;

import com.superops.movie.movie.Movie;
import com.superops.movie.theatre.auditorium.Auditorium;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shows")
public class Shows {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "start_time")
    private Instant showStartTime;

    @Column(name = "end_time")
    private Instant showEndTime;

    @Column(name = "is_house_full")
    private boolean isHouseFull;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audi_id")
    private Auditorium auditorium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Shows() {
//
    }

    public Shows(UUID showId) {
        this.id = showId;
    }

    public void movie(Movie movie) {
        this.movie = movie;
    }

    public void auditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(Instant showTime) {
        this.showStartTime = showTime;
    }

    public boolean isHouseFull() {
        return isHouseFull;
    }

    public void setHouseFull(boolean houseFull) {
        isHouseFull = houseFull;
    }

    public Instant getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(Instant showEndTime) {
        this.showEndTime = showEndTime;
    }
}
