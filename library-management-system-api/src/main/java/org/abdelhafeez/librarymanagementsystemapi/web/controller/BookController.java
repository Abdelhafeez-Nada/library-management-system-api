package org.abdelhafeez.librarymanagementsystemapi.web.controller;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
@Tag(name = "Books-APIs", description = "Contains Endpoints to interact with books")
public class BookController {

    private final BookService bookService;

    /**
     * Retrieves all books.
     * 
     * @return ResponseEntity containing a list of ResponseBookDto objects
     *         representing the books and HttpStatus.OK
     */
    @GetMapping()
    public ResponseEntity<List<ResponseBookDto>> getAllBooks() {
        // Call the service layer to retrieve all books
        List<ResponseBookDto> list = bookService.getAllBooks();
        // Return a ResponseEntity with the list of books and HTTP status OK
        return new ResponseEntity<List<ResponseBookDto>>(list, HttpStatus.OK);
    }

}
