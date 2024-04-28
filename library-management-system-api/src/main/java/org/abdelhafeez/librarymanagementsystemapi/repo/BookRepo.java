package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update books set available=false, updated_at=current_timestamp where id=:bookId")
    public int makeBookUnAvailable(@Param("bookId") long bookId);
}