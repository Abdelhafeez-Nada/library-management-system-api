package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BorrowingRecordRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BorrowingService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRecordRepo borrowingRepo;

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
