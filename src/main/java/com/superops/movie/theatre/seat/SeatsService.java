package com.superops.movie.theatre.seat;

import com.superops.movie.theatre.auditorium.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatsService {

    @Autowired
    private SeatsRepository auditoriumRepository;

    public Seats save(Seats seats) {
        return auditoriumRepository.save(seats);
    }

    public Seats save(Seats seats, UUID auditoriumId) {
        seats.auditorium(new Auditorium(auditoriumId));
        return auditoriumRepository.save(seats);
    }

    public List<Seats> save(Collection<Seats> seats, UUID auditoriumId) {
        seats = seats.stream().map(s -> {
            s.auditorium(new Auditorium(auditoriumId));
            return s;
        }).collect(Collectors.toSet());
        return auditoriumRepository.saveAll(seats);
    }

    public Set<Seats> filterDuplicateSeats(Collection<Seats> seats, UUID auditoruimId) {
      return   seats.stream().filter(s -> {
            return auditoriumRepository.existsByRowAndNumberAndAuditoriumId(s.getRow(), s.getNumber(), auditoruimId);
        }).collect(Collectors.toSet());
    }

    @Transactional
    public void deleteById(UUID id) {
        auditoriumRepository.deleteById(id);
    }
}
