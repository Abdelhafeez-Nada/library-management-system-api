package org.abdelhafeez.librarymanagementsystemapi.web.errorresponsehandler;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
