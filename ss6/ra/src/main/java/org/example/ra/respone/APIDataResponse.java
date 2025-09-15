package org.example.ra.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ra.model.Room;
import org.springframework.http.HttpStatus;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class APIDataResponse<T> {
    private Boolean success ;
    private String message;
    private Map<String,String> error;
    private T data;
    private HttpStatus status;
}
