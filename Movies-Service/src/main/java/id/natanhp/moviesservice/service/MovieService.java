package id.natanhp.moviesservice.service;

import id.natanhp.moviesservice.exception.InvalidUserInputException;
import id.natanhp.moviesservice.model.Movie;
import id.natanhp.moviesservice.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    @NonNull
    private final MovieRepository movieRepository;

    public Mono<Movie> findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Flux<Movie> findAllMoviesOnly() {
        return movieRepository.findAllMoviesOnly();
    }

    public Mono<Movie> createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Mono<Movie> updateMovie(Movie movie) {
        if (movie.getId() == null) {
            return Mono.empty()
                    .flatMap(__ -> Mono.error(new InvalidUserInputException("Empty movie Id")))
                    .cast(Movie.class);
        }

        return movieRepository.findById(movie.getId())
                .switchIfEmpty(Mono.error(new InvalidUserInputException("Movie does not exist")))
                .flatMap(__ -> movieRepository.save(movie))
                .cast(Movie.class);
    }

    public Mono<Void> deleteMovieById(Long id) {
        return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(new InvalidUserInputException("Movie does not exist")))
                .flatMap(__ -> movieRepository.deleteById(id))
                .cast(Void.class);
    }
}
