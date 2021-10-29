package id.natanhp.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestBodyMovie {

    private Long id;
    private String title;
    private String released;

    public RequestBodyMovie(Long id, String title, String released) {
        this.id = id;
        this.title = title;
        this.released = released;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }
}
