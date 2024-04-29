package org.abdelhafeez.librarymanagementsystemapi.web.controller;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Retreive all books at once, no pagination")
    public ResponseEntity<List<ResponseBookDto>> getAllBooks() {
        // Call the service layer to retrieve all books
        List<ResponseBookDto> list = bookService.getAllBooks();
        // Return a ResponseEntity with the list of books and HTTP status OK
        return new ResponseEntity<List<ResponseBookDto>>(list, HttpStatus.OK);
    }

    /**
     * Controller method for retrieving a page of books.
     * 
     * @param page The page number to retrieve (default: 0).
     * @param size The size of the page (default: 2).
     * @return ResponseEntity containing a page of ResponseBookDto objects
     *         representing the books and HttpStatus.OK.
     */
    @GetMapping("/page")
    @Operation(summary = "Retrieve a page of books")
    public ResponseEntity<Page<ResponseBookDto>> getBooksPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size) {
        // Retrieve the requested page of books from the service layer
        Page<ResponseBookDto> booksPage = bookService.getAllBooks(page, size);
        // Return a ResponseEntity with the page of books and HTTP status OK
        return new ResponseEntity<>(booksPage, HttpStatus.OK);
    }

}
