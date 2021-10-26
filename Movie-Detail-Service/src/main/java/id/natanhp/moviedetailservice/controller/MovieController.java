package id.natanhp.moviedetailservice.controller;

import id.natanhp.moviedetailservice.exception.DataNotFoundException;
import id.natanhp.moviedetailservice.service.MovieService;
import id.natanhp.moviedetailservice.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie-detail")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return BaseResponse.generateResponse("success", HttpStatus.OK, movieService.findById(id));
        } catch (DataNotFoundException e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.OK, null);
        } catch (Exception e) {
            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
