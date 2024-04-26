package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest()
public class BookRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepo bookRepo;

}
