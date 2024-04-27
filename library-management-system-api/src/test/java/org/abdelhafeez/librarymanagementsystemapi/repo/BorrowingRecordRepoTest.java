package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BorrowingRecordRepoTest {

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

}
