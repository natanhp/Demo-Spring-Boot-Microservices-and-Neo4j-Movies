package id.natanhp.data.entity;

import javax.persistence.Entity;

import id.natanhp.data.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaadin.fusion.Nonnull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies extends AbstractEntity {

    @Nonnull
    private String title;
    @Nonnull
    private String released;

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

}
