package org.abdelhafeez.librarymanagementsystemapi.service.contract;

public interface BorrowingService {
    public void borrowBook(Long bookId, Long patronId);
}