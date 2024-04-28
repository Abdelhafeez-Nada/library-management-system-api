package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

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
    @Transactional
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

    @Test
    @Transactional
    public void testUpdateBorrowingRecord() {
        // save borrowing rcord
        BorrowingRecord savedRecord = borrowingRecordRepo.save(borrowingRecord);
        // update borrowing record data
        savedRecord.setReturnDate(new Date());
        BorrowingRecord updatedRecord = borrowingRecordRepo.save(savedRecord);
        // assert that updates are commited successfully
        assertEquals(borrowingRecord.getId(), updatedRecord.getId());
        assertEquals(borrowingRecord.getReturnDate(), updatedRecord.getReturnDate());
    }

    @Test
    @Transactional
    public void testFindByBookIdAndPatronIdAndReturnDateIsNull() {
        // save borrowing rcord
        borrowingRecordRepo.save(borrowingRecord);
        // Find borrowing record by bookId, patronId, and null returnDate
        Optional<BorrowingRecord> foundRecordOptional = borrowingRecordRepo
                .findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId());

        // Assert that a borrowing record was found
        assertTrue(foundRecordOptional.isPresent());
        BorrowingRecord foundRecord = foundRecordOptional.get();
        assertEquals(borrowingRecord.getId(), foundRecord.getId());
        assertEquals(book.getId(), foundRecord.getBook().getId());
        assertEquals(patron.getId(), foundRecord.getPatron().getId());
        assertNull(foundRecord.getReturnDate());
    }

}
