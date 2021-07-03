package com.superops.movie.shows;

import com.superops.movie.movie.MovieRepository;
import com.superops.movie.theatre.auditorium.AuditoriumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ShowsResource {

    @Autowired
    private ShowsService showsService;
    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private MovieRepository movieRepository;

    private Logger log = LoggerFactory.getLogger(ShowsResource.class);

    @PreAuthorize("hasRole('THEATRE')")
    @PostMapping("/shows/{auditorium-id}/{movie-id}")
    public ResponseEntity createScreen(@RequestBody Shows shows,
                                       @PathVariable("auditorium-id") UUID auditoriumId,
                                       @PathVariable("movie-id") UUID movieId) {

        log.debug("REST request to save Shows : {}", shows);

        if (shows.getId() != null)
            return new ResponseEntity<>("A new show cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (!movieRepository.existsById(movieId))
            return new ResponseEntity<>("Invalid Movie", HttpStatus.BAD_REQUEST);

        if (!auditoriumRepository.existsById(auditoriumId))
            return new ResponseEntity<>("Invalid auditorium", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(showsService.createShow(shows, auditoriumId, movieId), HttpStatus.CREATED);
    }

    @GetMapping("/shows/by-movie/{movie-id}")
    public ResponseEntity<List<Shows>> getShowsByMovieId(@PathVariable("movie-id") UUID movieId) {

        log.debug("REST request to get shows by movie id : {}", movieId);

        return new ResponseEntity<>(showsService.getShowsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/shows/by-theatre/{threatre-id}")
    public ResponseEntity<List<Shows>> getShowsByTheatreId(@PathVariable("threatre-id") UUID theatreId) {

        log.debug("REST request to get shows by theatre id : {}", theatreId);

        return new ResponseEntity<>(showsService.getShowsByTheatreId(theatreId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('THEATRE')")
    @DeleteMapping("/shows/by-movie-id/{movie-id}")
    public ResponseEntity deleteShowsByMovieId(@PathVariable("movie-id") UUID movieId) {

        log.debug("REST request to delete shows by movie id : {}", movieId);
        showsService.deleteByMovieId(movieId);
        return new ResponseEntity<>("Shows are deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('THEATRE')")
    @DeleteMapping("/shows/{show-id}")
    public ResponseEntity deleteShowsById(@PathVariable("show-id") UUID showId) {

        log.debug("REST request to delete shows by show id : {}", showId);
        showsService.deleteById(showId);
        return new ResponseEntity<>("Shows are deleted", HttpStatus.OK);
    }
}
