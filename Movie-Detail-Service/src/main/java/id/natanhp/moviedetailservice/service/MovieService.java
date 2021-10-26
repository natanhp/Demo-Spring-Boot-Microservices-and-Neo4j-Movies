package id.natanhp.moviedetailservice.service;

import id.natanhp.moviedetailservice.exception.DataNotFoundException;
import id.natanhp.moviedetailservice.model.Movie;
import id.natanhp.moviedetailservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> findById(Long id) throws DataNotFoundException {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new DataNotFoundException("Movie not found");
        }
        return movie;
    }
}
