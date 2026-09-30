package com.abdul.catalogservice.adapter.in.web.handler;

import com.abdul.catalogservice.domain.common.exception.DomainValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Request validation failed.");
        return error(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ApiError> handleDomainValidation(
            DomainValidationException exception,
            HttpServletRequest request
    ) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception exception,
            HttpServletRequest request
    ) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request);
    }

    private ResponseEntity<ApiError> error(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        ApiError body = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(status).body(body);
    }
}
