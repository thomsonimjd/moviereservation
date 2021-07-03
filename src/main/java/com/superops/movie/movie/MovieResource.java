package com.superops.movie.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieResource {

    @Autowired
    private MovieService movieService;

    private Logger log = LoggerFactory.getLogger(MovieResource.class);

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','THEATRE')")
    @PostMapping("/movies")
    public ResponseEntity createMovie(@RequestBody Movie movie) {

        log.debug("REST request to save movie : {}", movie);

        if (movie.getId() != null)
            return new ResponseEntity<>("A new movie cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (!StringUtils.hasLength(movie.getTitle()))
            return new ResponseEntity<>("Empty Title", HttpStatus.BAD_REQUEST);

        if (!StringUtils.hasLength(movie.getLanguage()))
            return new ResponseEntity<>("Empty Language", HttpStatus.BAD_REQUEST);

        if (!StringUtils.hasLength(movie.getLanguage()))
            return new ResponseEntity<>("Empty release date", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        log.debug("REST request to get all movies");

        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

}
