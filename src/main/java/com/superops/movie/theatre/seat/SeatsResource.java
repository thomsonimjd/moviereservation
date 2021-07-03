package com.superops.movie.theatre.seat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SeatsResource {

    @Autowired
    private SeatsService seatService;
    @Autowired
    private SeatsRepository seatsRepository;

    private Logger log = LoggerFactory.getLogger(SeatsResource.class);

    @PreAuthorize("hasRole('THEATRE')")
    @PostMapping("/seats/{auditorium-id}")
    public ResponseEntity createSeats(@RequestBody Seats seat,
                                      @PathVariable("auditorium-id") UUID auditoriumId) {

        log.debug("REST request to save a seat : {}", seat);

        if (seat.getId() != null)
            return new ResponseEntity<>("A new screen cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (seat.getRow() == null)
            return new ResponseEntity<>("Empty seat row", HttpStatus.BAD_REQUEST);

        if (seat.getNumber() == null)
            return new ResponseEntity<>("Empty seat number", HttpStatus.BAD_REQUEST);

        if (seatsRepository.existsByRowAndNumberAndAuditoriumId(seat.getRow(), seat.getNumber(), auditoriumId))
            return new ResponseEntity("Duplicate seat", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(seatService.save(seat, auditoriumId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('THEATRE')")
    @PostMapping("/seats/add-bulk/{auditorium-id}")
    public ResponseEntity createBulkSeats(@RequestBody Set<Seats> seats,
                                          @PathVariable("auditorium-id") UUID auditoriumId) {

        log.debug("REST request to save seats : {}", seats);

        for (Seats seat : seats) {
            if (seat.getId() != null)
                return new ResponseEntity<>("A new screen cannot already have an ID", HttpStatus.BAD_REQUEST);

            if (seat.getRow() == null)
                return new ResponseEntity<>("Empty seat row", HttpStatus.BAD_REQUEST);

            if (seat.getNumber() == null)
                return new ResponseEntity<>("Empty seat number", HttpStatus.BAD_REQUEST);

            if (seatsRepository.existsByRowAndNumberAndAuditoriumId(seat.getRow(), seat.getNumber(), auditoriumId))
                return new ResponseEntity("Duplicate seat", HttpStatus.BAD_REQUEST);

        }
        if (!seatService.filterDuplicateSeats(seats, auditoriumId).isEmpty())
            return new ResponseEntity<>("Duplicate Seats", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(seatService.save(seats, auditoriumId), HttpStatus.CREATED);
    }

}
