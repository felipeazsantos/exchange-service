package dev.felipeazsantos.exchange_service.exceptions;


import dev.felipeazsantos.exchange_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedCurrencyException(UnsupportedCurrencyException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
