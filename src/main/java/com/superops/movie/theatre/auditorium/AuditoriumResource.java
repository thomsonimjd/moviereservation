package com.superops.movie.theatre.auditorium;

import com.superops.movie.security.SecurityUtils;
import com.superops.movie.theatre.TheatreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuditoriumResource {

    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private TheatreRepository theatreRepository;

    private Logger log = LoggerFactory.getLogger(AuditoriumResource.class);

    @PreAuthorize("hasRole('THEATRE')")
    @PostMapping("/auditorium/{theatre-id}")
    public ResponseEntity createNewAuditorium(@RequestBody Auditorium auditorium,
                                              @PathVariable("theatre-id") UUID theatreId) {

        log.debug("REST request to save auditorium : {}", auditorium);

        if (auditorium.getId() != null)
            return new ResponseEntity<>("A new auditorium cannot already have an ID", HttpStatus.BAD_REQUEST);


        if (StringUtils.hasLength(auditorium.getName()))
            return new ResponseEntity<>("Empty Audi name", HttpStatus.BAD_REQUEST);

        if (auditorium.getAudiNumber() == null)
            return new ResponseEntity<>("Empty Audi number", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(auditoriumService.save(auditorium, theatreId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('THEATRE')")
    @GetMapping("/auditorium/{theatre-id}")
    public ResponseEntity getAllMyAuditoriums(@PathVariable("theatre-id") UUID theatreId) {

        log.debug("REST request to get all my auditorium by theatre id: {}", theatreId);

        if (!theatreRepository.existsByIdAndUserId(theatreId, SecurityUtils.getUserId()))
            return new ResponseEntity<>("Logged in User does not have given theatre", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(auditoriumService.getAllAuditoriumsByTheatreId(theatreId), HttpStatus.CREATED);
    }
}
