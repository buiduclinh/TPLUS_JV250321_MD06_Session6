package org.example.ra.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    private String message;
    private int code;
    private Map<String,String> errors;
}
