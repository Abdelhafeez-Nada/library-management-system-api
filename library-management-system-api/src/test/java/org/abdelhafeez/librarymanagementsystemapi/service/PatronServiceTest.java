package org.abdelhafeez.librarymanagementsystemapi.service;

import org.abdelhafeez.librarymanagementsystemapi.repo.PatronRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.PatronServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    private BeanMapper beanMapper;

    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronServiceImpl patronServiceImpl;

}
