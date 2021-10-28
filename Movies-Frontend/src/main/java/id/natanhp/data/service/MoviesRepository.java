package id.natanhp.data.service;

import id.natanhp.data.entity.Movies;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaadin.fusion.Nonnull;

public interface MoviesRepository extends JpaRepository<Movies, Integer> {

}