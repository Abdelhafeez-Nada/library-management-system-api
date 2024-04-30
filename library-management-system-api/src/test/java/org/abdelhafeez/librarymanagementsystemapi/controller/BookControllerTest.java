package org.abdelhafeez.librarymanagementsystemapi.controller;

import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.web.controller.BookController;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
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
        RequestBookDto requestDto = RequestBookDto.builder().build();
        String requestJson = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
