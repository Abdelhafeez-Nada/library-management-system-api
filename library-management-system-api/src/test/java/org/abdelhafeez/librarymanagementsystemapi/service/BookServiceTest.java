package org.abdelhafeez.librarymanagementsystemapi.service;

import org.abdelhafeez.librarymanagementsystemapi.repo.BookRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.BookServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BeanMapper beanMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

}
