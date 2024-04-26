package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {

}
