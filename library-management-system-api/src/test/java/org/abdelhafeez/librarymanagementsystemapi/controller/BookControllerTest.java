package org.abdelhafeez.librarymanagementsystemapi.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.web.controller.BookController;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

        private static final String ENDPOINT_PATH = "/api/books";
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private BookService bookService;
        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testCreateBook_should400ReturnBadRequest() throws Exception {
                // Create an empty RequestBookDto
                RequestBookDto requestDto = RequestBookDto.builder().build();
                // Serialize the RequestBookDto to JSON
                String requestJson = objectMapper.writeValueAsString(requestDto);
                // Perform a POST request to the endpoint, sending the serialized JSON as
                // content
                mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestJson))
                                // Expect the status to be 400 (Bad Request)
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
                // Verify that the createBook method of bookService is not called
                verify(bookService, times(0)).createBook(requestDto);
        }

        @Test
        public void testCreateBook_shouldReturn201CreatedWithLocationHeader() throws Exception {
                // Prepare request DTO
                RequestBookDto requestDto = RequestBookDto.builder()
                                .title("title")
                                .author("author")
                                .publicationYear(Short.valueOf("2020"))
                                .isbn("ISBN")
                                .build();
                // Prepare mocked response DTO
                ResponseBookDto responseDto = ResponseBookDto.builder()
                                .id(1L)
                                .title("title")
                                .author("author")
                                .publicationYear(Short.valueOf("2020"))
                                .isbn("ISBN")
                                .build();
                // Mock the behavior of the bookService.createBook method
                when(bookService.createBook(requestDto)).thenReturn(responseDto);
                // Convert request DTO to JSON
                String requestJson = objectMapper.writeValueAsString(requestDto);
                // Perform POST request and validate response
                mockMvc.perform(
                                MockMvcRequestBuilders.post(ENDPOINT_PATH)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(requestJson))
                                // Expecting status 201 CREATED
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                // Expecting Location header with correct URL
                                .andExpect(MockMvcResultMatchers.header().string("Location",
                                                containsString("/books/1")))
                                // Expecting correct values for id,title,author,isbn,publicationYear
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(responseDto.getId().intValue())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is(responseDto.getTitle())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.author", is(responseDto.getAuthor())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", is(responseDto.getIsbn())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear",
                                                is(responseDto.getPublicationYear().intValue())));
                // Verify that bookService.createBook was called exactly once with the given
                // requestDto
                verify(bookService, times(1)).createBook(requestDto);
        }

        @Test
        public void testGetBookById_ShouldReturn404NotFound() throws Exception {
                // Prepare test data
                Long id = 1L;
                String path = ENDPOINT_PATH + "/" + id;
                // Mock the behavior of the bookService.getBookById method to throw
                // ResourceNotFoundException
                when(bookService.getBookById(id)).thenThrow(ResourceNotFoundException.class);
                // Perform GET request and validate response
                mockMvc.perform(MockMvcRequestBuilders.get(path))
                                // Expecting status 404 NOT FOUND
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
                // Verify that bookService.getBookById was called exactly once with the given id
                verify(bookService, times(1)).getBookById(id);
                // Assert that ResourceNotFoundException is thrown by bookService.getBookById
                assertThrows(ResourceNotFoundException.class, () -> {
                        bookService.getBookById(id);
                });
        }

        @Test
        public void testGetBookById_ShouldReturn200Ok() throws Exception {
                // Prepare test data
                Long id = 1L;
                String path = ENDPOINT_PATH + "/" + id;

                // Prepare mocked response DTO
                ResponseBookDto responseDto = ResponseBookDto.builder()
                                .id(id)
                                .title("title")
                                .author("author")
                                .publicationYear(Short.valueOf("2020"))
                                .isbn("ISBN")
                                .build();
                // Mock the behavior of the bookService.getBookById method
                when(bookService.getBookById(id)).thenReturn(responseDto);
                // Perform GET request and validate response
                mockMvc.perform(MockMvcRequestBuilders.get(path))
                                // Expecting JSON content
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                // Expecting status 200 OK
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                // Expecting correct values for id,title,author,isbn,publicationYear
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(responseDto.getId().intValue())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is(responseDto.getTitle())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.author", is(responseDto.getAuthor())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", is(responseDto.getIsbn())))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear",
                                                is(responseDto.getPublicationYear().intValue())));
                // Verify that bookService.getBookById was called exactly once with the given id
                verify(bookService, times(1)).getBookById(id);
        }

}
