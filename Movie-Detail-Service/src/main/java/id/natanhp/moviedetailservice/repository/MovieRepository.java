package id.natanhp.moviedetailservice.repository;

import id.natanhp.moviedetailservice.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
