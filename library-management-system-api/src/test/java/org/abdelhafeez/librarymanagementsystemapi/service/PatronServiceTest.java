package org.abdelhafeez.librarymanagementsystemapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.abdelhafeez.librarymanagementsystemapi.repo.PatronRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.PatronServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.junit.jupiter.api.Test;
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

    @Test
    public void testGetAllPatrons() {
        // Create a list of mock Patron entities
        List<Patron> entityList = createEntityList();
        // Stubbing the behavior of the repository to return the mock entity list
        when(patronRepo.findAll()).thenReturn(entityList);
        // Stubbing the behavior of the bean mapper to return a list of DTOs
        // based on the entity list provided
        when(beanMapper.mapEntityListToDtoList(entityList, ResponsePatronDto.class)).thenReturn(createDtoList());
        // Calling the method under test
        List<ResponsePatronDto> dtoList = patronServiceImpl.getAllPatrons();
        // Verifying that patronRepo.findAll() is called exactly once
        verify(patronRepo, times(1)).findAll();
        // Asserting that the returned DTO list is not null
        assertNotNull(dtoList);
        // Asserting that the size of the DTO list matches the size of the entity list
        assertEquals(entityList.size(), dtoList.size());
        // Asserting that each element in the DTO list corresponds to the respective
        // element in the entity list based on ID, name, and contact info
        for (int i = 0; i < entityList.size(); i++) {
            assertEquals(entityList.get(i).getId(), dtoList.get(i).getId());
            assertEquals(entityList.get(i).getName(), dtoList.get(i).getName());
            assertEquals(entityList.get(i).getContactInfo(), dtoList.get(i).getContactInfo());
        }
    }

    private List<Patron> createEntityList() {
        Patron patron1 = Patron.builder()
                .id(1l)
                .name("patron-1")
                .contactInfo("contact-1")
                .build();

        Patron patron2 = Patron.builder()
                .id(2l)
                .name("patron-2")
                .contactInfo("contact-2")
                .build();
        return Arrays.asList(patron1, patron2);
    }

    private List<ResponsePatronDto> createDtoList() {
        ResponsePatronDto dto1 = ResponsePatronDto.builder()
                .id(1l)
                .name("patron-1")
                .contactInfo("contact-1")
                .build();

        ResponsePatronDto dto2 = ResponsePatronDto.builder()
                .id(2l)
                .name("patron-2")
                .contactInfo("contact-2")
                .build();

        return Arrays.asList(dto1, dto2);
    }

}
