package org.abdelhafeez.librarymanagementsystemapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BookRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.impl.BookServiceImpl;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
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
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BeanMapper beanMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    public void testGetAllBooks() {
        // Create a list of mock Book entities
        List<Book> entityList = creatEntityList();
        // Stubbing the behavior of the repository to return the mock entity list
        when(bookRepo.findAll()).thenReturn(entityList);
        // Stubbing the behavior of the bean mapper to return a list of DTOs
        // based on the entity list provided
        when(beanMapper.mapEntityListToDtoList(entityList, ResponseBookDto.class)).thenReturn(createDtoList());
        // Calling the method under test
        List<ResponseBookDto> dtoList = bookServiceImpl.getAllBooks();
        // Verifying that bookRepo.findAll() is called exactly once
        verify(bookRepo, times(1)).findAll();
        // Asserting that the returned DTO list is not null
        assertNotNull(dtoList);
        // Asserting that the size of the DTO list matches the size of the entity list
        assertEquals(entityList.size(), dtoList.size());
        // Asserting that each element in the DTO list corresponds to the respective
        // element in the entity list based on ID, title, ISBN, and author
        for (int i = 0; i < entityList.size(); i++) {
            assertEquals(entityList.get(i).getId(), dtoList.get(i).getId());
            assertEquals(entityList.get(i).getTitle(), dtoList.get(i).getTitle());
            assertEquals(entityList.get(i).getIsbn(), dtoList.get(i).getIsbn());
            assertEquals(entityList.get(i).getAuthor(), dtoList.get(i).getAuthor());
        }
    }

    @Test
    public void testGetAllBooks_pagination() {
        // Prepare mock data
        // Create a list of mock Book entities
        List<Book> entityList = creatEntityList();
        // Create a list of mock ResponseBookDto objects
        List<ResponseBookDto> dtoList = createDtoList();
        // Create a pageable object for the first page with one item per page
        Pageable pageable = PageRequest.of(0, 1);
        // Create a mock Page<Book> object
        Page<Book> bookPage = new PageImpl<>(entityList);
        // Mock behavior of bookRepo to return the mock Page<Book> object when findAll
        // method is called with the provided pageable
        when(bookRepo.findAll(pageable)).thenReturn(bookPage);
        // Mock behavior of beanMapper to return the mock ResponseBookDto list when
        // mapEntityListToDtoList is called
        when(beanMapper.mapEntityListToDtoList(bookPage.getContent(), ResponseBookDto.class)).thenReturn(dtoList);
        // Call the method to be tested
        Page<ResponseBookDto> result = bookServiceImpl.getAllBooks(pageable.getPageNumber(), pageable.getPageSize());
        // Verify that bookRepo.findAll was called once with the provided pageable
        verify(bookRepo, times(1)).findAll(pageable);
        // Verify that beanMapper.mapEntityListToDtoList was called once with the
        // content of the mock page
        verify(beanMapper, times(1)).mapEntityListToDtoList(bookPage.getContent(), ResponseBookDto.class);
        // Ensure that the total number of elements in the result matches the size of
        // the DTO list
        assertEquals(result.getTotalElements(), dtoList.size());
        // Get the content of the result page
        List<ResponseBookDto> resultContent = result.getContent();
        for (int i = 0; i < resultContent.size(); i++) {
            // Ensure that each DTO in the result matches the corresponding DTO in the DTO
            // list
            assertEquals(dtoList.get(i), resultContent.get(i));
        }
    }

    @Test
    public void testGetBookById_EntityFound() {
        Book book = creatEntityList().get(0);
        // Stubbing repository behavior
        when(bookRepo.findById(book.getId())).thenReturn(Optional.of(book));
        // Stubbing mapper behavior
        when(beanMapper.mapEntityToDto(book, ResponseBookDto.class)).thenReturn(new ResponseBookDto());
        // Call the method under test
        ResponseBookDto responseBookDto = bookServiceImpl.getBookById(book.getId());
        // Verify that repository method was called with the correct ID
        verify(bookRepo, times(1)).findById(book.getId());
        // Assert that the returned DTO is not null
        assertNotNull(responseBookDto);
    }

    @Test
    public void testGetBookById_EntityNotFound() {
        // Prepare test data
        long id = 1L;
        // Stubbing repository behavior
        when(bookRepo.findById(id)).thenReturn(Optional.empty());
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.getBookById(id));
        // Verify that repository method was called with the correct ID
        verify(bookRepo, times(1)).findById(id);
    }

    @Test
    public void testCreateBook() throws BadRequestException {
        // Prepare test data
        RequestBookDto requestBookDto = RequestBookDto.builder()
                .author("author-1")
                .title("title-1")
                .isbn("isbn-1")
                .publicationYear(Short.valueOf("2024"))
                .build();
        Book book = creatEntityList().get(0);
        ResponseBookDto responseBookDto = createDtoList().get(0);
        // Stubbing mapper behavior to map DTO to entity
        when(beanMapper.mapDtoToEntity(requestBookDto, Book.class)).thenReturn(book);
        // Stubbing repository behavior to save the entity and return it
        when(bookRepo.save(any())).thenReturn(book);
        // Stubbing mapper behavior to map entity to DTO
        when(beanMapper.mapEntityToDto(book, ResponseBookDto.class)).thenReturn(responseBookDto);
        // Call the method under test
        ResponseBookDto createdBookDto = bookServiceImpl.createBook(requestBookDto);
        // Verify that the mapper's mapDtoToEntity method was called with the provided
        // DTO
        verify(beanMapper, times(1)).mapDtoToEntity(requestBookDto, Book.class);
        // Verify that the repository's save method was called once with the mapped
        // entity
        verify(bookRepo, times(1)).save(any());
        // Verify that the mapper's mapEntityToDto method was called once with the saved
        // entity
        verify(beanMapper, times(1)).mapEntityToDto(book, ResponseBookDto.class);
        // Assert that the returned DTO matches the expected response DTO
        assertEquals(responseBookDto, createdBookDto);
    }

    @Test
    public void testUpdateBook_SuccessfulUpdate() {
        // Prepare test data
        Long id = 1L;
        RequestBookDto requestBookDto = RequestBookDto.builder()
                .author("updated_author-1")
                .title("updated_title-1")
                .isbn("updated_isbn-1")
                .publicationYear(Short.valueOf("2023"))
                .build();
        ResponseBookDto expectedResponse = createDtoList().get(0);
        expectedResponse.setAuthor(requestBookDto.getAuthor());
        expectedResponse.setTitle(requestBookDto.getTitle());
        expectedResponse.setIsbn(requestBookDto.getIsbn());
        expectedResponse.setPublicationYear(requestBookDto.getPublicationYear());
        Book originalBook = creatEntityList().get(0);
        // Update the original book with values from the request DTO
        originalBook.setAuthor(requestBookDto.getAuthor());
        originalBook.setTitle(requestBookDto.getTitle());
        originalBook.setIsbn(requestBookDto.getIsbn());
        originalBook.setPublicationYear(requestBookDto.getPublicationYear());
        // Stub repository behavior
        when(bookRepo.findById(id)).thenReturn(Optional.of(originalBook));
        when(bookRepo.save(originalBook)).thenReturn(originalBook);
        // Stub mapper behavior
        when(beanMapper.mapEntityToDto(originalBook, ResponseBookDto.class)).thenReturn(expectedResponse);
        // Call the method under test
        ResponseBookDto updated = bookServiceImpl.updateBook(id, requestBookDto);

        // Assert that result is correct
        assertEquals(expectedResponse, updated);
        // Verify repository method calls
        verify(bookRepo, times(1)).findById(id);
        verify(bookRepo, times(1)).save(originalBook);
        // Verify mapper method call
        verify(beanMapper, times(1)).mapEntityToDto(originalBook, ResponseBookDto.class);
    }

    @Test
    public void testUpdateBook_EntityNotFound() {
        // Prepare test data
        long id = 1L;
        RequestBookDto dto = RequestBookDto.builder().build();
        // Stubbing repository behavior
        when(bookRepo.findById(id)).thenReturn(Optional.empty());
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.updateBook(id, dto));
        // Verify that repository method was called with the correct ID
        verify(bookRepo, times(1)).findById(id);
    }

    @Test
    public void testUpdateBook_InvalidInputParameter() {
        // Prepare test data
        Long id = null;
        RequestBookDto dto = RequestBookDto.builder().build();
        // Call the method under test and assert that it throws
        // ResourceNotFoundException
        assertThrows(BadRequestException.class, () -> bookServiceImpl.updateBook(id, dto));
    }

    @Test
    public void testDeleteBook_SuccessfulDeletion() {
        // Prepare test data
        Long id = 1L;
        Book book = creatEntityList().get(0);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));
        // Call the method under test
        bookServiceImpl.deleteBook(id);
        // Verify that the findById method of the repository was called once with the
        // correct ID
        verify(bookRepo, times(1)).findById(id);
        // Verify that the deleteById method of the repository was called once with the
        // correct ID
        verify(bookRepo, times(1)).deleteById(id);
    }

    private List<Book> creatEntityList() {
        // create list of entity
        Book bookEntity1 = Book.builder()
                .id(1l)
                .author("author-1")
                .title("title-1")
                .isbn("isbn-1")
                .publicationYear(Short.valueOf("2024"))
                .createdAt(new Date())
                .available(true)
                .enabled(true)
                .build();
        Book bookEntity2 = Book.builder()
                .id(2l)
                .author("author-2")
                .title("title-2")
                .isbn("isbn-2")
                .publicationYear(Short.valueOf("2024"))
                .createdAt(new Date())
                .available(true)
                .enabled(true)
                .build();
        return Arrays.asList(bookEntity1, bookEntity2);
    }

    private List<ResponseBookDto> createDtoList() {
        ResponseBookDto responseBookDto1 = ResponseBookDto.builder()
                .id(1l)
                .author("author-1")
                .title("title-1")
                .isbn("isbn-1")
                .publicationYear(Short.valueOf("2024"))
                .createdAt(new Date())
                .available(true)
                .enabled(true)
                .build();
        ResponseBookDto responseBookDto2 = ResponseBookDto.builder()
                .id(2l)
                .author("author-2")
                .title("title-2")
                .isbn("isbn-2")
                .publicationYear(Short.valueOf("2024"))
                .createdAt(new Date())
                .available(true)
                .enabled(true)
                .build();
        return Arrays.asList(responseBookDto1, responseBookDto2);
    }

}
