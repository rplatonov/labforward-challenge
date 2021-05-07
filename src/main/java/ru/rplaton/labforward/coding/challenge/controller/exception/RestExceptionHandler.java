package ru.rplaton.labforward.coding.challenge.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.rplaton.labforward.coding.challenge.dto.ApiError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        final ConstraintViolation<?> lastViolation = ex.getConstraintViolations().iterator().next();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(lastViolation.getMessage()));
    }
}
