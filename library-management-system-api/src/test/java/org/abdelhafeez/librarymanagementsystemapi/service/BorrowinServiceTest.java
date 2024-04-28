package org.abdelhafeez.librarymanagementsystemapi.service;

import org.abdelhafeez.librarymanagementsystemapi.repo.BorrowingRecordRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BorrowinServiceTest {

    @Mock
    private BorrowingRecordRepo borrowingRepo;
    @Mock
    private BookService bookService;
    @Mock
    private PatronService patronService;

}
