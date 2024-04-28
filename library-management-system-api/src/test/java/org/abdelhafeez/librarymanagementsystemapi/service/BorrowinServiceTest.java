package org.abdelhafeez.librarymanagementsystemapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BorrowingRecordRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.BorrowingServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BorrowinServiceTest {

    @Mock
    private BorrowingRecordRepo borrowingRepo;
    @Mock
    private BookService bookService;
    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    @Test
    public void testBorrowBook_Success() throws BadRequestException, ResourceNotFoundException {
        // Mock book and patron
        ResponseBookDto book = new ResponseBookDto();
        book.setAvailable(true);
        book.setEnabled(true);
        ResponsePatronDto patron = new ResponsePatronDto();
        patron.setEnabled(true);
        // Mock behavior of bookService and patronService
        when(bookService.getBookById(anyLong())).thenReturn(book);
        when(patronService.getPatronById(anyLong())).thenReturn(patron);

        // Mock borrowingRepo.save() to return null
        when(borrowingRepo.save(any(BorrowingRecord.class))).thenReturn(null);
        // Call the method
        borrowingService.borrowBook(1L, 2L);
        // Verify interactions
        verify(borrowingRepo, times(1)).save(any(BorrowingRecord.class));
        verify(bookService, times(1)).makeBookUnAvailable(1L);
    }

    @Test
    public void testBorrowBook_NotAvailableBook() throws BadRequestException, ResourceNotFoundException {
        // Mock book to be not available
        ResponseBookDto book = new ResponseBookDto();
        book.setAvailable(false);
        book.setEnabled(true);
        // Mock behavior of bookService
        when(bookService.getBookById(anyLong())).thenReturn(book);
        // Call the method and assert BadRequestException is thrown
        assertThrows(BadRequestException.class, () -> borrowingService.borrowBook(1L, 2L));
        // Ensure that borrowingRepo.save() and bookService.makeBookUnAvailable() are
        // not called
        verify(borrowingRepo, never()).save(any(BorrowingRecord.class));
        verify(bookService, never()).makeBookUnAvailable(anyLong());
    }

}
