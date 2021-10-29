package id.natanhp.data.endpoint;

import id.natanhp.data.dto.GeneralResponse;
import id.natanhp.data.dto.RequestBodyMovie;
import id.natanhp.data.entity.Movies;
import id.natanhp.data.service.MoviesService;

import java.util.List;
import java.util.Optional;

import com.vaadin.fusion.Endpoint;

import org.vaadin.artur.helpers.GridSorter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import com.vaadin.fusion.Nonnull;
import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Endpoint
@AnonymousAllowed
public class MoviesEndpoint {

    private MoviesService service;
    private WebClient webClient;

    public MoviesEndpoint(@Autowired MoviesService service, WebClient.Builder wBuilder) {
        this.service = service;
        this.webClient = wBuilder.baseUrl("http://localhost:5002/api/movies")
        .filter(basicAuthentication("a28J0f4pJF6clwqtI0c7", "J4lnhhl4avt21URqzFMQ"))
        .build();
    }

    @Nonnull
    public List<@Nonnull Movies> list(int offset, int limit, @Nonnull List<@Nonnull GridSorter> sortOrder) {
        return webClient.get()
        .retrieve()
        .toEntity((Class<GeneralResponse<List<Movies>>>)(Object)GeneralResponse.class)
        .block()
        .getBody().getContent();
    }

    public Optional<Movies> get(@Nonnull Integer id) {
        JsonNode movieDetailJson = webClient.get()
        .uri(uri -> uri.path("/"+id).build())
        .retrieve()
        .toEntity(JsonNode.class)
        .block()
        .getBody();

        return Optional.of(new Movies(Long.valueOf(movieDetailJson.get("content").get("id").asText()), movieDetailJson.get("content").get("title").asText(), movieDetailJson.get("content").get("released").asText()));
    }

    public Optional<Movies> create(Movies movie) {
        movie.setId(null);
        JsonNode movieDetailJson = webClient.post()
        .bodyValue(movie)
        .retrieve()
        .toEntity(JsonNode.class)
        .block()
        .getBody();

        return Optional.of(new Movies(Long.valueOf(movieDetailJson.get("content").get("id").asText()), movieDetailJson.get("content").get("title").asText(), movieDetailJson.get("content").get("released").asText()));
    }

    @Nonnull
    public Movies update(@Nonnull Movies entity, int id) {
        RequestBodyMovie requestMovie = new RequestBodyMovie((long) id, entity.getTitle(), entity.getReleased());
        JsonNode movieDetailJson = webClient.put()
        .bodyValue(requestMovie)
        .retrieve()
        .toEntity(JsonNode.class)
        .block()
        .getBody();

        System.out.println(movieDetailJson);
        return new Movies(Long.valueOf(movieDetailJson.get("content").get("id").asText()), movieDetailJson.get("content").get("title").asText(), movieDetailJson.get("content").get("released").asText());
    }

    public void delete(@Nonnull Integer id) {
        webClient.delete()
        .uri(uri -> uri.path("/"+id).build())
        .retrieve()
        .toEntity(JsonNode.class)
        .block()
        .getBody();
    }

    public int count() {
        return service.count();
    }

}
