package org.abdelhafeez.librarymanagementsystemapi.repo;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update borrowing_records set return_date=current_timestamp where book_id=:bookId and patron_id=:patronId")
    public int updateReturnDate(@Param("bookId") long bookId, @Param("patronId") long patronId);

    // query method to find borrowing records by bookId and patronId
    List<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}
