package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class BorrowingRecordRepoTest {

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    public void setup() {
        // caretae and save bbok
        book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setPublicationYear((short) 2020);
        book.setIsbn("1234567890");
        book.setAvailable(true);
        book = bookRepo.save(book);
        // create and save patron
        patron = new Patron();
        patron.setName("patron-1");
        patron.setContactInfo("john@example.com");
        patron = patronRepo.save(patron);
        // create and save borrowin record
        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(new Date());
    }

    @Test
    @Transactional
    public void testInsertBorrowingRecord() {
        // save borrowing rcord
        BorrowingRecord savedRecord = borrowingRecordRepo.save(borrowingRecord);
        // assert that borrowin record saved successfully
        assertNotNull(savedRecord.getId());
        assertEquals(book.getId(), savedRecord.getBook().getId());
        assertEquals(patron.getId(), savedRecord.getPatron().getId());
    }

}
