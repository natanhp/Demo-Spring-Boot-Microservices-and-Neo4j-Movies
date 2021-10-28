package id.natanhp.data.service;

import id.natanhp.data.entity.Movies;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movies, Integer> {

}