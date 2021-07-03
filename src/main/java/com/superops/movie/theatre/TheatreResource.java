package com.superops.movie.theatre;

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
public class TheatreResource {

    @Autowired
    private TheatreService theatreService;

    private Logger log = LoggerFactory.getLogger(TheatreResource.class);

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','THEATRE')")
    @PostMapping("/theatres")
    public ResponseEntity createUser(@RequestBody Theatre theatre) {

        log.debug("REST request to save Theatre : {}", theatre);

        if (theatre.getId() != null)
            return new ResponseEntity<>("A new theatre cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (!StringUtils.hasLength(theatre.getName()))
            return new ResponseEntity<>("Empty theatre name", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(theatreService.save(theatre), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @GetMapping("/theatres/all")
    public ResponseEntity getAllTheatres() {
        log.debug("REST request to all Theatres ");
        return new ResponseEntity<>(theatreService.getAllTheatres(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('THEATRE')")
    @GetMapping("/theatres/all-my-theares")
    public ResponseEntity<List<Theatre>> getAllMyTheatres() {
        log.debug("REST request to get all Theatres ");
        List<Theatre> theatres = theatreService.getMyTheatres();
        return new ResponseEntity<>(theatres, HttpStatus.OK);
    }
}
