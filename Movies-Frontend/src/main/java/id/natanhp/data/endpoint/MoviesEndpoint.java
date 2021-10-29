package id.natanhp.data.endpoint;

import id.natanhp.data.dto.GeneralResponse;
import id.natanhp.data.entity.Movies;
import id.natanhp.data.service.MoviesService;

import java.util.List;
import java.util.Optional;

import com.vaadin.fusion.Endpoint;

import org.vaadin.artur.helpers.GridSorter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import com.vaadin.fusion.Nonnull;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
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

        return Optional.of(new Movies(movieDetailJson.get("content").get("title").asText(), movieDetailJson.get("content").get("released").asText()));
    }

    @Nonnull
    public Movies update(@Nonnull Movies entity) {
        return service.update(entity);
    }

    public void delete(@Nonnull Integer id) {
        service.delete(id);
    }

    public int count() {
        return service.count();
    }

}
