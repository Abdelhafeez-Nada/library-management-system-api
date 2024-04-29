package org.abdelhafeez.librarymanagementsystemapi.web.errorresponsehandler;

import java.util.ArrayList;
import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorResponseHandler {

    /**
     * Handles ResourceNotFoundException and returns a ResponseEntity with an
     * ErrorResponseDto containing
     * error details and HTTP status 404 Not Found.
     * 
     * @param exception The ResourceNotFoundException to handle.
     * @return ResponseEntity containing an ErrorResponseDto representing the error
     *         and HttpStatus.NOT_FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException exception) {
        // Create an ErrorResponseDto with error details
        ErrorResponseDto dto = new ErrorResponseDto("404", exception.getMessage());
        // Return a ResponseEntity with the ErrorResponseDto and HTTP status 404 Not
        // Found
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BadRequestException and returns a ResponseEntity with an
     * ErrorResponseDto containing
     * error details and HTTP status 400 Bad Request.
     * 
     * @param exception The BadRequestException to handle.
     * @return ResponseEntity containing an ErrorResponseDto representing the error
     *         and HttpStatus.BAD_REQUEST.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> badRequestException(BadRequestException exception) {
        // Create an ErrorResponseDto with error details
        ErrorResponseDto dto = new ErrorResponseDto("400", exception.getMessage());
        // Return a ResponseEntity with the ErrorResponseDto and HTTP status 400 Bad
        // Request
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity with an
     * ErrorResponseDto containing
     * error details and HTTP status 400 Bad Request.
     * 
     * @param ex The MethodArgumentNotValidException to handle.
     * @return ResponseEntity containing an ErrorResponseDto representing the error
     *         and HttpStatus.BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Collect all validation errors from the exception
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = String.format("%s %s", ((FieldError) error).getField(),
                    error.getDefaultMessage());
            errors.add(errorMessage);
        });
        // Join all error messages with a newline character
        String message = String.join("\n", errors);
        // Create an ErrorResponseDto with error details
        ErrorResponseDto dto = new ErrorResponseDto("400", message);
        // Return a ResponseEntity with the ErrorResponseDto and HTTP status 400 Bad
        // Request
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}
