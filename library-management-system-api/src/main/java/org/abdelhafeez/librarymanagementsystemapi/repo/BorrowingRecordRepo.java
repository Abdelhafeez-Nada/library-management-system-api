package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.abdelhafeez.librarymanagementsystemapi.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {

}
