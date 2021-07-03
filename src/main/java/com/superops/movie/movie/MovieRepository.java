package com.superops.movie.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    @Query("SELECT m.durationMin FROM Movie m WHERE m.id=?1")
    int getMovieTotalMinutes(UUID movieId);
}
