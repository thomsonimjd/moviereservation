package com.superops.movie.shows;

import com.superops.movie.movie.Movie;
import com.superops.movie.movie.MovieRepository;
import com.superops.movie.theatre.auditorium.Auditorium;
import com.superops.movie.theatre.auditorium.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class ShowsService {

    @Autowired
    private ShowsRepository showsRepository;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Shows save(Shows shows) {
        return showsRepository.save(shows);
    }

    public Shows createShow(Shows shows, UUID auditoriumId, UUID movieId) {
        shows.movie(new Movie(movieId));
        shows.auditorium(new Auditorium(auditoriumId));
        shows.setShowEndTime(shows.getShowStartTime().plus(movieRepository.getMovieTotalMinutes(movieId), ChronoUnit.MINUTES));
        return showsRepository.save(shows);
    }

    @Transactional
    public void deleteById(UUID id) {
        showsRepository.deleteById(id);
    }

    public List<Shows> getShowsByMovieId(UUID movieId) {
        return showsRepository.findByMovieId(movieId);
    }

    public List<Shows> getShowsByTheatreId(UUID theatreId) {
        List<UUID> audiIds = auditoriumRepository.findAllAuditoriumIdsByTheatreId(theatreId);
        return showsRepository.findByAuditoriumIdIn(audiIds);
    }

    @Transactional
    public void deleteByMovieId(UUID movieId) {
        showsRepository.deleteAllByMovieId(movieId);
    }
}
