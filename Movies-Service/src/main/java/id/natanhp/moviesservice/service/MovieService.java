package id.natanhp.moviesservice.service;

import id.natanhp.moviesservice.model.Movie;
import id.natanhp.moviesservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Iterable<Movie> findAllMoviesOnly() {
        return movieRepository.findAllMoviesOnly();
    }
}
