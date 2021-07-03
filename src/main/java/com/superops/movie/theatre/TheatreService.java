package com.superops.movie.theatre;

import com.superops.movie.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre save(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public List<Theatre> getMyTheatres() {
        return theatreRepository.findAllByUserId(SecurityUtils.getUserId());
    }

    @Transactional
    public void deleteById(UUID id) {
        theatreRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return theatreRepository.existsByName(name);
    }
}
