package org.abdelhafeez.librarymanagementsystemapi.repo;

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

}
