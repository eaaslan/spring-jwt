package com.jwt.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        // If it's specifically a token error, use UNAUTHORIZED
        if (ex.getMessage().contains("token")) {
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}