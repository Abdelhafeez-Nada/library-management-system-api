package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BorrowingRecordRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BorrowingService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRecordRepo borrowingRepo;

    private final BookService bookService;

    private final PatronService patronService;

    @Override
    public void borrowBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrowBook'");
    }

    @Override
    public void returnBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

}
