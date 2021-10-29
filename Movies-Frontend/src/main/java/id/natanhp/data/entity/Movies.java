package id.natanhp.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;

import id.natanhp.data.AbstractEntity;
import id.natanhp.data.dto.RequestBodyMovie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaadin.fusion.Nonnull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies extends AbstractEntity {

    private Long id;

    @Nonnull
    private String title;
    @Nonnull
    private String released;

    public Movies() {
    }

    public Movies(Long id, String title, String released) {
        this.id = id;
        this.title = title;
        this.released = released;
    }

    public Movies(RequestBodyMovie requestBodyMovie) {
        this.id = requestBodyMovie.getId();
        this.title = requestBodyMovie.getTitle();
        this.released = requestBodyMovie.getReleased();
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getReleased() {
        return released;
    }
    public void setReleased(String released) {
        this.released = released;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

}
