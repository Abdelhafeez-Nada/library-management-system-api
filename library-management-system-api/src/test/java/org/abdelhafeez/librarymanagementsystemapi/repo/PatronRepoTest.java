package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void testUpdatePatron() {
        // create new patron and persist it
        Patron patron = Patron.builder().name("ptron-1").contactInfo("123456789").build();
        Patron savedPatron = patronRepo.save(patron);
        // update patron info
        savedPatron.setName("patron-2");
        savedPatron.setContactInfo("987654321");
        // merge patron updates
        patronRepo.save(savedPatron);
        // retrieve the updated patron
        Patron updatedPatron = entityManager.find(Patron.class, savedPatron.getId());
        // assert that the patron is updated successfully
        assertEquals("patron-2", updatedPatron.getName());
        assertEquals("987654321", updatedPatron.getContactInfo());
    }

    @Test
    public void testFindPatronById() {
        // create new patron and persist it
        Patron patron = Patron.builder().name("ptron-1").contactInfo("123456789").build();
        patron = entityManager.persist(patron);
        // retrieve the saved patron by its ID
        Patron foundPatron = patronRepo.findById(patron.getId()).orElse(null);
        // assert that the retrieved patron matches the saved one
        assertEquals(patron, foundPatron);
    }
}
