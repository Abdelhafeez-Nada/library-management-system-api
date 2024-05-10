package org.abdelhafeez.librarymanagementsystemapi.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.persistence.Query;

/**
 * @DataJpaTest
 *              1. It scans the `@Entity` classes and Spring Data JPA
 *              repositories.
 *              2. Set the `spring.jpa.show-sql` property to true and enable the
 *              SQL queries logging.
 *              3. Default, JPA test data are transactional and roll back at the
 *              end of each test;
 *              it means we do not need to clean up saved or modified table data
 *              after each test.
 *              4. Replace the application DataSource, run and configure the
 *              embedded database on classpath.
 */
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

    @Test
    public void testFindAllPatrons() {
        // execute JPQL to count all patrons
        Query query = entityManager.getEntityManager().createQuery("SELECT COUNT(p) FROM Patron p");
        Long count = (Long) query.getSingleResult();
        // save two patrons
        Patron patron1 = Patron.builder().name("patron-1").contactInfo("123456789").build();
        entityManager.persist(patron1);
        Patron patron2 = Patron.builder().name("patron-2").contactInfo("987654321").build();
        entityManager.persist(patron2);
        // retrieve all patrons from the repository
        List<Patron> patrons = patronRepo.findAll();
        // assert all patrons have been retrieved
        assertEquals(count + 2, patrons.size());
    }

    @Test
    public void testDeletePatron() {
        // create new patron and persist it
        Patron patron = Patron.builder().name("patron-1").contactInfo("123456789").build();
        patron = entityManager.persist(patron);
        // delete the patron from the repository
        patronRepo.delete(patron);
        // try to find deleted patron
        Patron shouldBeNull = patronRepo.findById(patron.getId()).orElse(null);
        // assert that the patron is deleted successfully
        assertNull(shouldBeNull);
    }
}
