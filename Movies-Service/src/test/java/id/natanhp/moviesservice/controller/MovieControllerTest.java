package id.natanhp.moviesservice.controller;

import id.natanhp.moviesservice.dto.BaseResponse;
import id.natanhp.moviesservice.dto.MovieDTO;
import id.natanhp.moviesservice.exception.InvalidUserInputException;
import id.natanhp.moviesservice.model.Movie;
import id.natanhp.moviesservice.service.MovieService;
import id.natanhp.moviesservice.util.ModelMapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@WebFluxTest(controllers = {MovieController.class})
class MovieControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private MovieService movieService;

    private MovieDTO movieDTO1, movieDTO2;
    private Movie movie1, movie2;

    @BeforeEach
    void setUp() {
        movieDTO1 = new MovieDTO(1L, "Title Movie 1", 2021, "Tagline Movie 1", new ArrayList<>());
        movieDTO2 = new MovieDTO(2L, "Title Movie 2", 2019, "Tagline Movie 2", new ArrayList<>());

        ModelMapper realModelMapper = ModelMapperHelper.getInstance().getModelMapper();

        movie1 = realModelMapper.map(movieDTO1, Movie.class);
        movie2 = realModelMapper.map(movieDTO2, Movie.class);
    }

    @Test
    void findAll() {
        Mockito.when(movieService.findAllMoviesOnly()).thenReturn(Flux.just(movie1, movie2));

        BaseResponse<List<MovieDTO>> expectedReturn = new BaseResponse<>(
                "success",
                HttpStatus.OK.value(),
                new ArrayList<>(List.of(movieDTO1, movieDTO2))
        );

        StepVerifier.create(client.get()
                        .uri("/api/movies")
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<List<MovieDTO>>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void create() {
        Mockito.when(movieService.createMovie(movie1)).thenReturn(Mono.just(movie1));

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "success",
                HttpStatus.CREATED.value(),
                movieDTO1
        );

        StepVerifier.create(client.post()
                        .uri("/api/movies")
                        .body(Mono.just(movieDTO1), MovieDTO.class)
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void update() {
        Mockito.when(movieService.updateMovie(movie1)).thenReturn(Mono.just(movie1));

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "success",
                HttpStatus.OK.value(),
                movieDTO1
        );

        StepVerifier.create(client.put()
                        .uri("/api/movies")
                        .body(Mono.just(movieDTO1), MovieDTO.class)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void updateWithEmptyId() {
        Mockito.when(movieService.updateMovie(movie1)).thenReturn(Mono.error(
                new InvalidUserInputException("Empty movie Id"))
        );

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "Empty movie Id",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                null
        );

        StepVerifier.create(client.put()
                        .uri("/api/movies")
                        .body(Mono.just(movieDTO1), MovieDTO.class)
                        .exchange()
                        .expectStatus().is4xxClientError()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void updateNonExistingData() {
        Mockito.when(movieService.updateMovie(movie1)).thenReturn(Mono.error(
                new InvalidUserInputException("Movie does not exist"))
        );

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "Movie does not exist",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                null
        );

        StepVerifier.create(client.put()
                        .uri("/api/movies")
                        .body(Mono.just(movieDTO1), MovieDTO.class)
                        .exchange()
                        .expectStatus().is4xxClientError()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void deleteById() {
        Mockito.when(movieService.deleteMovieById(1L)).thenReturn(Mono.empty());

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "success",
                HttpStatus.OK.value(),
                null
        );

        StepVerifier.create(client.delete()
                        .uri(String.format("/api/movies/%d", movieDTO1.getId()))
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }

    @Test
    void deleteByIWithNonExistingId() {
        Mockito.when(movieService.deleteMovieById(123L)).thenReturn(Mono.error(
                new InvalidUserInputException("Movie does not exist"))
        );

        BaseResponse<MovieDTO> expectedReturn = new BaseResponse<>(
                "Movie does not exist",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                null
        );

        StepVerifier.create(client.delete()
                        .uri("/api/movies/123")
                        .exchange()
                        .expectStatus().is4xxClientError()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(new ParameterizedTypeReference<BaseResponse<MovieDTO>>() {
                        })
                        .getResponseBody())
                .expectNext(expectedReturn)
                .verifyComplete();
    }
}