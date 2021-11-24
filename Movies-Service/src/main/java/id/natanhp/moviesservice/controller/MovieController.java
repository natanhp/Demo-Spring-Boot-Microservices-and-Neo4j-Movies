package id.natanhp.moviesservice.controller;

import id.natanhp.moviesservice.dto.MovieDTO;
import id.natanhp.moviesservice.model.Movie;
import id.natanhp.moviesservice.service.MovieService;
import id.natanhp.moviesservice.util.BaseResponse;
import id.natanhp.moviesservice.util.ModelMapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ModelMapper modelMapper;

//    @NonNull
//    private final RestTemplate restTemplate;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
        modelMapper = ModelMapperHelper.getInstance().getModelMapper();
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> findAll() {
        try {
            return movieService.findAllMoviesOnly()
                    .map(movie -> modelMapper.map(movie, MovieDTO.class))
                    .collectList()
                    .map(movies -> BaseResponse.generateResponse("success", HttpStatus.OK, movies));
        } catch (Exception e) {
            return Mono.just(BaseResponse
                    .generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

//    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse detailById(@PathVariable Long id) {
//        try {
//            var movieObject = restTemplate.getForObject(String.format("http://movie-detail-service/api/movie-detail/%d", id), Object.class);
//            return ResponseEntity.ok(movieObject);
//        } catch (Exception e) {
//            return BaseResponse.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
//        }
//    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> create(@RequestBody MovieDTO movie) {
        try {
            return movieService.createMovie(modelMapper.map(movie, Movie.class))
                    .map(createdMovie -> BaseResponse.generateResponse(
                            "success",
                            HttpStatus.CREATED,
                            modelMapper.map(createdMovie, MovieDTO.class))
                    );
        } catch (Exception e) {
            return Mono.just(BaseResponse
                    .generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> update(@RequestBody MovieDTO movie) {
        try {
            return movieService.updateMovie(modelMapper.map(movie, Movie.class))
                    .map(updatedMovie -> BaseResponse.generateResponse("success", HttpStatus.OK, modelMapper
                            .map(updatedMovie, MovieDTO.class)))
                    .onErrorResume(e -> Mono
                            .just(e.getMessage())
                            .map(errorMessage -> BaseResponse
                                    .generateResponse(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY, null)));
        } catch (Exception e) {
            return Mono.just(BaseResponse
                    .generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable Long id) {
        try {
            return movieService.deleteMovieById(id)
                    .thenReturn(BaseResponse.generateResponse("success", HttpStatus.OK, null))
                    .onErrorResume(e -> Mono
                            .just(e.getMessage())
                            .map(errorMessage -> BaseResponse
                                    .generateResponse(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY, null)));
        } catch (Exception e) {
            return Mono.just(BaseResponse
                    .generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }
}
