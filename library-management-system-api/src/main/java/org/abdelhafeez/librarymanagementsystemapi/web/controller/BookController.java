package org.abdelhafeez.librarymanagementsystemapi.web.controller;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    /**
     * Controller method for retrieving a book by its ID.
     * 
     * @param bookId The ID of the book to retrieve.
     * @return ResponseEntity containing the ResponseBookDto representing the book
     *         and HttpStatus.OK if the book is found,
     *         otherwise HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{bookId}")
    @Operation(summary = "Retrieve a book by its ID")
    public ResponseEntity<ResponseBookDto> getBookById(@PathVariable("bookId") Long bookId) {
        // Retrieve the book by its ID from the service layer
        ResponseBookDto dto = bookService.getBookById(bookId);
        // Return a ResponseEntity with the book and HTTP status OK
        return new ResponseEntity<ResponseBookDto>(dto, HttpStatus.OK);
    }

    /**
     * Controller method for creating a new book.
     * 
     * @param dto The RequestBookDto containing information about the new book.
     * @return ResponseEntity containing the newly created ResponseBookDto
     *         representing the created book and HttpStatus.CREATED.
     */
    @PostMapping("/books")
    @Operation(summary = "Create a new book")
    public ResponseEntity<ResponseBookDto> createBook(@Valid @RequestBody RequestBookDto dto) {
        // Call the service layer to create the new book
        ResponseBookDto responseBookDto = bookService.createBook(dto);
        // Return a ResponseEntity with the created book and HTTP status CREATED
        return new ResponseEntity<>(responseBookDto, HttpStatus.CREATED);
    }

}
