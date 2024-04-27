package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PatronRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatronRepo patronRepo;

    @Test
    public void testInsertPatron() {
        // create new patron and persist it
        Patron patron = Patron.builder().name("John Doe").contactInfo("123456789").build();
        Patron savedPatron = patronRepo.save(patron);
        // assert patron is inserted successfully
        assertTrue(savedPatron.getId() > 0);
    }
}
