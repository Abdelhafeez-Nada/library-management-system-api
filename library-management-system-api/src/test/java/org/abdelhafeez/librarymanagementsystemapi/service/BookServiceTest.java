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

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BeanMapper beanMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    public void testGetAllBooks() throws Exception {
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
    public void testGetBookById_EntityFound() throws Exception {
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
    public void testCreateBook() throws BadRequestException, Exception {
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
