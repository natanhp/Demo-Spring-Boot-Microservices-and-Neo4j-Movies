package id.natanhp.moviedetailservice.service;

import id.natanhp.moviedetailservice.model.Movie;
import id.natanhp.moviedetailservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }
}
