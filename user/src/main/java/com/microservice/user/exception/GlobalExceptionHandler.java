package com.microservice.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        ) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            org.springframework.web.bind.MethodArgumentNotValidException ex, WebRequest request) {

        // Extract first field error message
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                errorMessage,
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(EmailAlreadyExistException alreadyExistException,WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                alreadyExistException.getMessage(),
                request.getDescription(false)
        ) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex,WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        ) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
