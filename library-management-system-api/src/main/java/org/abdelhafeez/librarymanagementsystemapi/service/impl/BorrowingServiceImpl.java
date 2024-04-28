package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import java.util.Date;
import java.util.Optional;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BorrowingRecordRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BorrowingService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRecordRepo borrowingRepo;

    private final BookService bookService;

    private final PatronService patronService;

    /**
     * Borrow a book for a patron.
     *
     * @param bookId   The ID of the book to be borrowed.
     * @param patronId The ID of the patron borrowing the book.
     * @throws BadRequestException       If the request is invalid.
     * @throws ResourceNotFoundException If the requested resource is not found.
     */
    @Override
    @Transactional(rollbackFor = { ResourceNotFoundException.class, BadRequestException.class, RuntimeException.class,
            Exception.class })
    public void borrowBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {
        // Retrieve book information
        ResponseBookDto book = bookService.getBookById(bookId);
        // Retrieve patron information
        ResponsePatronDto patron = patronService.getPatronById(patronId);
        // Check if the book is available for borrowing
        if (!book.getAvailable())
            throw new BadRequestException("Book Not Returned Yet!");
        // Check if the book is enabled
        if (!book.getEnabled())
            throw new BadRequestException("Book Not Enabled!");
        // Check if the patron is enabled
        if (!patron.getEnabled())
            throw new BadRequestException("Patron Not Enabled!");
        // Create book entity
        Book bookEntity = Book.builder().id(bookId).build();
        // Create patron entity
        Patron patronEntity = Patron.builder().id(patronId).build();
        // Create borrowing record entity
        BorrowingRecord borrowingEntity = BorrowingRecord.builder()
                .book(bookEntity)
                .patron(patronEntity)
                .build();
        // Save borrowing record
        borrowingRepo.save(borrowingEntity);
        // Mark the book as unavailable
        bookService.makeBookUnAvailable(bookId);
    }

    /**
     * Return a borrowed book.
     *
     * @param bookId   The ID of the book to be returned.
     * @param patronId The ID of the patron returning the book.
     * @throws BadRequestException       If the request is invalid.
     * @throws ResourceNotFoundException If the requested resource is not found.
     */
    @Override
    @Transactional(rollbackFor = { ResourceNotFoundException.class, BadRequestException.class, RuntimeException.class,
            Exception.class })
    public void returnBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {

        // get borrowing record
        Optional<BorrowingRecord> borrowingOptional = borrowingRepo.findByBookIdAndPatronIdAndReturnDateIsNull(bookId,
                patronId);
        if (!borrowingOptional.isPresent())
            throw new BadRequestException("there is no borrowin record");
        BorrowingRecord borrowingRecord = borrowingOptional.get();
        // update borrowing record
        borrowingRecord.setReturnDate(new Date());
        // save borrowingRecord entity
        borrowingRepo.save(borrowingRecord);
        // update book availability
        bookService.makeBookAvailable(bookId);
    }

    // @Override
    // @Transactional(rollbackFor = { ResourceNotFoundException.class,
    // BadRequestException.class, RuntimeException.class,
    // Exception.class })
    // public void returnBook(Long bookId, Long patronId) throws
    // BadRequestException, ResourceNotFoundException {
    // // Check if both book and patron exist
    // bookService.getBookById(bookId);
    // patronService.getPatronById(patronId);
    // // Update return date in borrowing record
    // borrowingRepo.updateReturnDate(bookId, patronId);
    // // Mark the book as available
    // bookService.makeBookAvailable(bookId);
    // }

}
