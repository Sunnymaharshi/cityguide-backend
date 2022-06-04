package com.cityguide.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleNotFound(NotFoundException notFoundException) {

        String response = notFoundException.getMessage() + " Not Found";
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<?> handleUnAuthorized(UnAuthorisedException unAuthorisedException) {

        String response = "UNAUTHORIZED";
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnknownError(Exception exception) {


        String response = "Error: " + exception.getMessage();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }
}
