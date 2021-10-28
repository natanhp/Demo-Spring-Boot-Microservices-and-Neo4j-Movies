package id.natanhp.data.service;

import id.natanhp.data.entity.Movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import com.vaadin.fusion.Nonnull;

@Service
public class MoviesService extends CrudService<Movies, Integer> {

    private MoviesRepository repository;

    public MoviesService(@Autowired MoviesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MoviesRepository getRepository() {
        return repository;
    }

}
