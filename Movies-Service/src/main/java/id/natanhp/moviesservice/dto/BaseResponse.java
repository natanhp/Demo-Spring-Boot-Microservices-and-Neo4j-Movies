package id.natanhp.moviesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private String message;
    private int status;
    private T content;
}
