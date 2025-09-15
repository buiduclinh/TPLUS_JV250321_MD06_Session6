package org.example.ra.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GEH {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String,String> fieldError = new HashMap<>();
        for(FieldError error : fieldErrors){
            fieldError.put(error.getField(),error.getDefaultMessage());
        }
        ApiError apiError = new ApiError();
        apiError.setMessage("MethodArgumentNotValidException");
        apiError.setCode(400);
        apiError.setErrors(fieldError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        ApiError apiError = new ApiError();
        apiError.setMessage("HttpMessageNotReadableException");
        apiError.setCode(400);
        apiError.setErrors(Map.of("", apiError.getErrors().getOrDefault("message", "")));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
