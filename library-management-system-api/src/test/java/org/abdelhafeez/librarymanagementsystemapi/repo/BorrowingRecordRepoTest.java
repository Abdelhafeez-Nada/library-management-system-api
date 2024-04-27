package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BorrowingRecordRepoTest {

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    @Test
    public void testInsertBorrowingRecord() {
        // caretae and save bbok
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setPublicationYear((short) 2020);
        book.setIsbn("1234567890");
        book.setAvailable(true);
        book = bookRepo.save(book);
        // create and save patron
        Patron patron = new Patron();
        patron.setName("patron-1");
        patron.setContactInfo("john@example.com");
        patron = patronRepo.save(patron);
        // create and save borrowin record
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(new Date());
        BorrowingRecord savedRecord = borrowingRecordRepo.save(borrowingRecord);
        // assert that borrowin record saved successfully
        assertNotNull(savedRecord.getId());
        assertEquals(book.getId(), savedRecord.getBook().getId());
        assertEquals(patron.getId(), savedRecord.getPatron().getId());
    }

}
