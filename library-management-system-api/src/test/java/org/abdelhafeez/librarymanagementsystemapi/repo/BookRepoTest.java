package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.persistence.Query;

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

    @Test
    public void testFindAllBooks() {
        // execute JPQL to count all books
        Query query = entityManager.getEntityManager().createQuery("SELECT COUNT(b) FROM Book b");
        Long count = (Long) query.getSingleResult();
        // save two books
        Book book1 = Book.builder().author("author-1").isbn("isbn-1").title("title-1").build();
        entityManager.persist(book1);
        Book book2 = Book.builder().author("author-2").isbn("isbn-2").title("title-2").build();
        entityManager.persist(book2);
        // retrieve all books from the repository
        List<Book> books = bookRepo.findAll();
        // assert all books have been reterived
        assertEquals(count + 2, books.size());
    }

}
