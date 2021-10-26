package id.natanhp.moviesservice.repository;

import id.natanhp.moviesservice.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(@Param("title") String title);
}
