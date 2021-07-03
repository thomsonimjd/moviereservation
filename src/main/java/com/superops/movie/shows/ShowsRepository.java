package com.superops.movie.shows;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ShowsRepository extends JpaRepository<Shows, UUID> {

    List<Shows> findByMovieId(UUID id);

    List<Shows> findByAuditoriumIdIn(List<UUID> ids);

    void deleteAllByMovieId(UUID id);

    @Query("SELECT s.auditorium.id FROM Shows s WHERE s.id=?1")
    UUID getAuditoriumId(UUID showId);
}
