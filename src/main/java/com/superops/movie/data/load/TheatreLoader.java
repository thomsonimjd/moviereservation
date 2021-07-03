package com.superops.movie.data.load;

import com.superops.movie.theatre.Theatre;
import com.superops.movie.theatre.TheatreService;
import com.superops.movie.theatre.auditorium.Auditorium;
import com.superops.movie.theatre.auditorium.AuditoriumService;
import com.superops.movie.theatre.seat.Seats;
import com.superops.movie.theatre.seat.SeatsService;
import com.superops.movie.enums.SeatRow;
import com.superops.movie.user.User;
import com.superops.movie.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TheatreLoader {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private SeatsService seatsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditoriumService auditoriumService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void addTheatres() {
        Theatre theatre = addPVRCinemas();
        if (!theatreService.existsByName(theatre.getName())) {
            Optional<User> pvrUser = userRepository.findByUsername("pvr");
            if (pvrUser.isPresent()) {
                Theatre pvr = theatreService.save(addPVRCinemas());
                pvr.user(pvrUser.get());
                pvr = theatreService.save(pvr);

                Auditorium auditorium = addAuditorium(pvr);
                auditorium = auditoriumService.save(auditorium);
                List<Seats> seats = generateSeats();
                Set<Seats> duplicateSeats = seatsService.filterDuplicateSeats(seats, auditorium.getId());
                if (duplicateSeats.isEmpty()) {
                    seatsService.save(seats, auditorium.getId());
                }
            }
        }
    }

    private Theatre addPVRCinemas() {
        Theatre theatre = new Theatre();
        theatre.setAddress("Chennai - Grand Mall");
        theatre.setName("PVR Velachery");
        return theatre;
    }

    private Auditorium addAuditorium(Theatre theatre) {
        Auditorium audi1 = new Auditorium();
        audi1.setAudiNumber(1l);
        audi1.setName("Audi 1");
        audi1.theatre(theatre);
        return audi1;
    }

    private List<Seats> generateSeats() {
        return Arrays.asList(SeatRow.A, SeatRow.B, SeatRow.C).stream().flatMap(seatRow -> {
            Seats seat1 = new Seats(seatRow, 1);
            Seats seat2 = new Seats(seatRow, 2);
            Seats seat3 = new Seats(seatRow, 3);

            return Arrays.asList(seat1, seat2, seat3).stream();
        }).collect(Collectors.toList());
    }
}
