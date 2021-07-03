package com.superops.movie.theatre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TheatreRepository extends JpaRepository<Theatre, UUID> {
    boolean existsByName(String name);

    List<Theatre> findAllByUserId(UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);
}
