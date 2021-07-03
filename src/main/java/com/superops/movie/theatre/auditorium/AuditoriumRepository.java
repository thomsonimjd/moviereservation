package com.superops.movie.theatre.auditorium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AuditoriumRepository extends JpaRepository<Auditorium, UUID> {

    @Query("SELECT a.id FROM Auditorium a WHERE a.theatre.id=?1")
    List<UUID> findAllAuditoriumIdsByTheatreId(UUID theatreId);

    List<Auditorium> findAllByTheatreId(UUID theatreId);

}
