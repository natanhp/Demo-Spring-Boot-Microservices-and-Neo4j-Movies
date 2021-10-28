package id.natanhp.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralResponse<T> {
    private String message;
    private T content;
    private int status;

    public T getContent() {
        return content;
    }
}
