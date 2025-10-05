package org.arcsoft.javaprofinal.adapter.in.http;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.arcsoft.javaprofinal.adapter.in.http.dto.ErrorResponse;
import org.arcsoft.javaprofinal.application.domain.model.exception.LimitExceededException;
import org.arcsoft.javaprofinal.application.domain.model.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleLimitExceededException(LimitExceededException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getAllErrors().stream()
                .map(err -> err.unwrap(ConstraintViolation.class))
                .map(cv -> cv.getPropertyPath().toString() + ": " + cv.getMessage())
                .toList();
        return ResponseEntity.badRequest().body(new ErrorResponse("Method argument not valid", errors));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception e) {
        log.error("Error executing request: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }
}
