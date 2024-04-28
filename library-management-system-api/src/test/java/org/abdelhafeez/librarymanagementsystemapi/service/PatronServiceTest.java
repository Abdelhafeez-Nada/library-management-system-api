package org.abdelhafeez.librarymanagementsystemapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.PatronRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.PatronServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestPatronDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Test
    public void testGetAllPatrons_pagination() {
        // Prepare mock data
        // Create a list of mock Patron entities
        List<Patron> entityList = createEntityList();
        // Create a list of mock ResponsePatronDto objects
        List<ResponsePatronDto> dtoList = createDtoList();
        // Create a pageable object for the first page with one item per page
        Pageable pageable = PageRequest.of(0, 1);
        // Create a mock Page<Patron> object
        Page<Patron> patronPage = new PageImpl<>(entityList);
        // Mock behavior of patronRepo to return the mock Page<Patron> object when
        // findAll
        // method is called with the provided pageable
        when(patronRepo.findAll(pageable)).thenReturn(patronPage);
        // Mock behavior of beanMapper to return the mock ResponsePatronDto list when
        // mapEntityListToDtoList is called
        when(beanMapper.mapEntityListToDtoList(patronPage.getContent(), ResponsePatronDto.class)).thenReturn(dtoList);
        // Call the method to be tested
        Page<ResponsePatronDto> result = patronServiceImpl.getAllPatrons(pageable.getPageNumber(),
                pageable.getPageSize());
        // Verify that patronRepo.findAll was called once with the provided pageable
        verify(patronRepo, times(1)).findAll(pageable);
        // Verify that beanMapper.mapEntityListToDtoList was called once with the
        // content of the mock page
        verify(beanMapper, times(1)).mapEntityListToDtoList(patronPage.getContent(), ResponsePatronDto.class);
        // Ensure that the total number of elements in the result matches the size of
        // the DTO list
        assertEquals(result.getTotalElements(), dtoList.size());
        // Get the content of the result page
        List<ResponsePatronDto> resultContent = result.getContent();
        for (int i = 0; i < resultContent.size(); i++) {
            // Ensure that each DTO in the result matches the corresponding DTO in the DTO
            // list
            assertEquals(dtoList.get(i), resultContent.get(i));
        }
    }

    @Test
    public void testGetPatronById_EntityFound() {
        Patron patron = createEntityList().get(0);
        // Stubbing repository behavior
        when(patronRepo.findById(patron.getId())).thenReturn(Optional.of(patron));
        // Stubbing mapper behavior
        when(beanMapper.mapEntityToDto(patron, ResponsePatronDto.class)).thenReturn(new ResponsePatronDto());
        // Call the method under test
        ResponsePatronDto responsePatronDto = patronServiceImpl.getPatronById(patron.getId());
        // Verify that repository method was called with the correct ID
        verify(patronRepo, times(1)).findById(patron.getId());
        // Assert that the returned DTO is not null
        assertNotNull(responsePatronDto);
    }

    @Test
    public void testGetPatronById_EntityNotFound() {
        // Prepare test data
        long id = 1L;
        // Stubbing repository behavior
        when(patronRepo.findById(id)).thenReturn(Optional.empty());
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> patronServiceImpl.getPatronById(id));
        // Verify that repository method was called with the correct ID
        verify(patronRepo, times(1)).findById(id);
    }

    @Test
    public void testCreatePatron() throws BadRequestException {
        // Prepare test data
        RequestPatronDto requestPatronDto = RequestPatronDto.builder()
                .name("name-1")
                .contactInfo("contact-1")
                .build();
        Patron patron = createEntityList().get(0);
        ResponsePatronDto responsePatronDto = createDtoList().get(0);
        // Stubbing mapper behavior to map DTO to entity
        when(beanMapper.mapDtoToEntity(requestPatronDto, Patron.class)).thenReturn(patron);
        // Stubbing repository behavior to save the entity and return it
        when(patronRepo.save(any())).thenReturn(patron);
        // Stubbing mapper behavior to map entity to DTO
        when(beanMapper.mapEntityToDto(patron, ResponsePatronDto.class)).thenReturn(responsePatronDto);
        // Call the method under test
        ResponsePatronDto createdPatronDto = patronServiceImpl.createPatron(requestPatronDto);
        // Verify that the mapper's mapDtoToEntity method was called with the provided
        // DTO
        verify(beanMapper, times(1)).mapDtoToEntity(requestPatronDto, Patron.class);
        // Verify that the repository's save method was called once with the mapped
        // entity
        verify(patronRepo, times(1)).save(any());
        // Verify that the mapper's mapEntityToDto method was called once with the saved
        // entity
        verify(beanMapper, times(1)).mapEntityToDto(patron, ResponsePatronDto.class);
        // Assert that the returned DTO matches the expected response DTO
        assertEquals(responsePatronDto, createdPatronDto);
    }

    @Test
    public void testUpdatePatron_SuccessfulUpdate() {
        // Prepare test data
        Long id = 1L;
        RequestPatronDto requestPatronDto = RequestPatronDto.builder()
                .name("updated_name-1")
                .contactInfo("updated_contact-1")
                .build();
        ResponsePatronDto expectedResponse = createDtoList().get(0);
        expectedResponse.setName(requestPatronDto.getName());
        expectedResponse.setContactInfo(requestPatronDto.getContactInfo());
        Patron originalPatron = createEntityList().get(0);
        // Update the original patron with values from the request DTO
        originalPatron.setName(requestPatronDto.getName());
        originalPatron.setContactInfo(requestPatronDto.getContactInfo());
        // Stub repository behavior
        when(patronRepo.findById(id)).thenReturn(Optional.of(originalPatron));
        when(patronRepo.save(originalPatron)).thenReturn(originalPatron);
        // Stub mapper behavior
        when(beanMapper.mapEntityToDto(originalPatron, ResponsePatronDto.class)).thenReturn(expectedResponse);
        // Call the method under test
        ResponsePatronDto updated = patronServiceImpl.updatePatron(id, requestPatronDto);

        // Assert that result is correct
        assertEquals(expectedResponse, updated);
        // Verify repository method calls
        verify(patronRepo, times(1)).findById(id);
        verify(patronRepo, times(1)).save(originalPatron);
        // Verify mapper method call
        verify(beanMapper, times(1)).mapEntityToDto(originalPatron, ResponsePatronDto.class);
    }

    @Test
    public void testUpdatePatron_EntityNotFound() {
        // Prepare test data
        long id = 1L;
        RequestPatronDto dto = RequestPatronDto.builder().build();
        // Stubbing repository behavior
        when(patronRepo.findById(id)).thenReturn(Optional.empty());
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> patronServiceImpl.updatePatron(id, dto));
        // Verify that repository method was called with the correct ID
        verify(patronRepo, times(1)).findById(id);
    }

    @Test
    public void testUpdatePatron_InvalidInputParameter() {
        // Prepare test data
        Long id = null;
        RequestPatronDto dto = RequestPatronDto.builder().build();
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(BadRequestException.class, () -> patronServiceImpl.updatePatron(id, dto));
    }

    @Test
    public void testSoftDeletePatron_SuccessfulDeletion() {
        // Prepare test data
        Long id = 1L;
        Patron patron = createEntityList().get(0);
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));
        // Call the method under test
        assertDoesNotThrow(() -> patronServiceImpl.deletePatron(id));
        // Verify that findById and save methods of the repository were called once with
        // the correct ID and patron
        verify(patronRepo, times(1)).findById(id);
        verify(patronRepo, times(1)).save(patron);
        // Assert that the patron's enabled status is set to false
        assertFalse(patron.getEnabled());
    }

    @Test
    public void testDeletePatron_SuccessfulDeletion() {
        // Prepare test data
        Long id = 1L;
        Patron patron = createEntityList().get(0);
        when(patronRepo.findById(id)).thenReturn(Optional.of(patron));
        // Call the method under test
        patronServiceImpl.deletePatron(id);
        // Verify that the findById method of the repository was called once with the
        // correct ID
        verify(patronRepo, times(1)).findById(id);
        // Verify that the deleteById method of the repository was called once with the
        // correct ID
        verify(patronRepo, times(1)).deleteById(id);
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
