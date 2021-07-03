package com.superops.movie.theatre.seat;

import com.superops.movie.enums.SeatRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeatsRepository extends JpaRepository<Seats, UUID> {

    boolean existsByRowAndNumberAndAuditoriumId(SeatRow row, int number, UUID auditoriumId);

    Optional<Seats> findByRowAndNumberAndAuditoriumId(SeatRow row, int number, UUID auditoriumId);
}
