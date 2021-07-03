package com.superops.movie.theatre.auditorium;

import com.superops.movie.theatre.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class AuditoriumService {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    public Auditorium save(Auditorium auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    public Auditorium save(Auditorium auditorium, UUID theatreId) {
        auditorium.theatre(new Theatre(theatreId));
        return auditoriumRepository.save(auditorium);
    }

    public List<Auditorium> getAllAuditoriumsByTheatreId(UUID theatreId) {
        return auditoriumRepository.findAllByTheatreId(theatreId);
    }

    @Transactional
    public void deleteById(UUID id) {
        auditoriumRepository.deleteById(id);
    }
}
