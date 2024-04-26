package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest()
public class BookRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepo bookRepo;

    @Test
    public void testInsertBook() {
        // create new book and persist it
        Book book = Book.builder().author("author").isbn("isbn").title("title").build();
        Book savedBook = bookRepo.save(book);
        // assert book is inserted successfully
        assertTrue(savedBook.getId() > 0);
    }

    @Test
    public void testUpdateBook() {
        // create new book and persist it
        Book book = Book.builder().author("author").isbn("isbn").title("title").build();
        Book savedBook = bookRepo.save(book);
        // update book info
        savedBook.setTitle("Updated Title");
        savedBook.setAuthor("Updated Author");
        // merge book updates
        bookRepo.save(savedBook);
        // retrieve the updated book
        Book updatedBook = entityManager.find(Book.class, savedBook.getId());
        // assert that the book is updated successfully
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
    }

    @Test
    public void testFindBookById() {
        // create new book and persist it
        Book book = Book.builder().author("author").isbn("isbn").title("title").build();
        book = entityManager.persist(book);
        // retrieve the saved book by its ID
        Book foundBook = bookRepo.findById(book.getId()).orElse(null);
        // assert that the retrieved book matches the saved one
        assertEquals(book, foundBook);
    }

}
