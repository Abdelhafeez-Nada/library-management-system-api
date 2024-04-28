package org.abdelhafeez.librarymanagementsystemapi.service.contract;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;

public interface BorrowingService {
    public void borrowBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException;

    public void returnBook(Long bookId, Long patronId) throws BadRequestException, ResourceNotFoundException;
}