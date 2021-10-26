package id.natanhp.moviesservice.controller;

import id.natanhp.moviesservice.exception.InvalidUserInputException;
import id.natanhp.moviesservice.model.Movie;
import id.natanhp.moviesservice.service.MovieService;
import id.natanhp.moviesservice.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        try {
            return BaseResponse.generateResponse("success", HttpStatus.OK, movieService.findAllMoviesOnly());
        } catch (Exception e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Movie movie) {
        try {
            return BaseResponse.generateResponse("success", HttpStatus.CREATED, movieService.createMovie(movie));
        } catch (Exception e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestBody Movie movie) {
        try {
            return BaseResponse.generateResponse("success", HttpStatus.OK, movieService.updateMovie(movie));
        } catch (InvalidUserInputException e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, null);
        } catch (Exception e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            movieService.deleteMovieById(id);
            return BaseResponse.generateResponse("success", HttpStatus.OK, null);
        } catch (Exception e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
