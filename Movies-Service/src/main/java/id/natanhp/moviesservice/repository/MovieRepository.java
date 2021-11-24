package id.natanhp.moviesservice.repository;

import id.natanhp.moviesservice.model.Movie;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveNeo4jRepository<Movie, Long> {
    Mono<Movie> findByTitle(@Param("title") String title);

    @Query("MATCH (m:Movie) RETURN m")
    Flux<Movie> findAllMoviesOnly();
}
