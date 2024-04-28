package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BorrowingService;

public class BorrowingServiceImpl implements BorrowingService {

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
