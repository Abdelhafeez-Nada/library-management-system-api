package org.abdelhafeez.librarymanagementsystemapi.service.impl;

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

    @Override
    @Transactional(rollbackFor = { ResourceNotFoundException.class, BadRequestException.class, RuntimeException.class,
            Exception.class })
    public void borrowBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {
        ResponseBookDto book = bookService.getBookById(bookId);
        ResponsePatronDto patron = patronService.getPatronById(patronId);
        if (!book.getAvailable())
            throw new BadRequestException("Book Not Returned Yet!");
        if (!book.getEnabled())
            throw new BadRequestException("Book Not Enabled!");
        if (!patron.getEnabled())
            throw new BadRequestException("Patron Not Enabled!");
        Book bookEntity = Book.builder().id(bookId).build();
        Patron patronEntity = Patron.builder().id(patronId).build();
        BorrowingRecord boorrowEntity = BorrowingRecord.builder()
                .book(bookEntity)
                .patron(patronEntity)
                .build();
        borrowingRepo.save(boorrowEntity);
        bookService.makeBookUnAvailable(bookId);
    }

    @Override
    public void returnBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

}
