package id.natanhp.moviedetailservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object content) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());
        response.put("content", content);

        return new ResponseEntity<>(response, status);
    }
}
