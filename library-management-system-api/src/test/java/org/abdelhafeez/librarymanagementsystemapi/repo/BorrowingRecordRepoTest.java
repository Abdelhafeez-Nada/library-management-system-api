package org.abdelhafeez.librarymanagementsystemapi.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
public class BorrowingRecordRepoTest {

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    @MockBean
    private BookRepo bookRepo;

    @MockBean
    private PatronRepo patronRepo;

}
