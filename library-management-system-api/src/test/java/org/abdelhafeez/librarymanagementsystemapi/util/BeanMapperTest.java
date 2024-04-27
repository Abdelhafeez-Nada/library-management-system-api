package org.abdelhafeez.librarymanagementsystemapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanMapperTest {

    @Autowired
    private BeanMapper beanMapper;

    @Test
    public void testMapEntityToDto() {
        // create new book entity
        Book bookEntity = Book.builder()
                .id(1l)
                .author("author")
                .title("title")
                .isbn("isbn")
                .publicationYear(Short.valueOf("2024"))
                .createdAt(new Date())
                .available(true)
                .enabled(true)
                .build();
        // map entity to dto
        ResponseBookDto dto = beanMapper.mapEntityToDto(bookEntity, ResponseBookDto.class);
        // assert that mapping done sucessfully
        assertNotNull(dto);
        assertEquals(bookEntity.getId(), dto.getId());
        assertEquals(bookEntity.getAuthor(), dto.getAuthor());
        assertEquals(bookEntity.getTitle(), dto.getTitle());
    }
}
