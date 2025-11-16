package org.example.mercadolibre.config;

import org.example.mercadolibre.exception.DnaHashCalculationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DnaHashCalculationException.class)
    public ResponseEntity<String> handleDnaHashCalculationException(DnaHashCalculationException ex) {
        // Log the exception for debugging purposes
        // logger.error("Internal server error during DNA hash calculation", ex);
        return new ResponseEntity<>("Internal server error during DNA analysis.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
