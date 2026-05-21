package com.genai.retail.auth.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {

    Map<String, Object> response =
        new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("message", ex.getMessage());
    response.put("status", HttpStatus.BAD_REQUEST.value());

    return ResponseEntity
        .badRequest()
        .body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(
      MethodArgumentNotValidException ex) {

    Map<String, Object> errors =
        new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
            errors.put(
                error.getField(),
                error.getDefaultMessage()
            )
        );

    return ResponseEntity
        .badRequest()
        .body(errors);
  }
}