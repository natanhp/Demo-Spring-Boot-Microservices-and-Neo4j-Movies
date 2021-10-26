package id.natanhp.moviesservice.repository;

import id.natanhp.moviesservice.model.Movie;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(@Param("title") String title);

    @Query("MATCH (m:Movie) RETURN m")
    Iterable<Movie> findAllMoviesOnly();
}
