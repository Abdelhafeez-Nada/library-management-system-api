package org.abdelhafeez.librarymanagementsystemapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanMapperTest {

    @Autowired
    private BeanMapper beanMapper;

    private List<Book> entityList;
    private RequestBookDto requestBookDto;

    @BeforeEach
    public void init() {
        entityList = creatEntityList();
        requestBookDto = RequestBookDto.builder()
                .author("author")
                .title("title")
                .isbn("isbn")
                .publicationYear(Short.valueOf("2022"))
                .build();
    }

    @Test
    public void testMapEntityToDto() {
        // create new book entity
        Book bookEntity = entityList.get(0);
        // map entity to dto
        ResponseBookDto dto = beanMapper.mapEntityToDto(bookEntity, ResponseBookDto.class);
        // assert that mapping done sucessfully
        assertNotNull(dto);
        assertEquals(bookEntity.getId(), dto.getId());
        assertEquals(bookEntity.getAuthor(), dto.getAuthor());
        assertEquals(bookEntity.getTitle(), dto.getTitle());
    }

    @Test
    public void testMapEntityListToDtoList() {
        // map entity list to dto list
        List<ResponseBookDto> dtoList = beanMapper.mapEntityListToDtoList(entityList, ResponseBookDto.class);
        // assert that mapping done sucessfully
        assertNotNull(dtoList);
        assertEquals(entityList.size(), dtoList.size());
        assertEquals(entityList.get(0).getId(), dtoList.get(0).getId());
        assertEquals(entityList.get(0).getTitle(), dtoList.get(0).getTitle());
        assertEquals(entityList.get(0).getIsbn(), dtoList.get(0).getIsbn());
        assertEquals(entityList.get(0).getAuthor(), dtoList.get(0).getAuthor());

        assertEquals(entityList.size(), dtoList.size());
        assertEquals(entityList.get(1).getId(), dtoList.get(1).getId());
        assertEquals(entityList.get(1).getTitle(), dtoList.get(1).getTitle());
        assertEquals(entityList.get(1).getIsbn(), dtoList.get(1).getIsbn());
        assertEquals(entityList.get(1).getAuthor(), dtoList.get(1).getAuthor());
    }

    @Test
    public void testMapDtoToEntity() {
        // map dto to entity
        Book book = beanMapper.mapDtoToEntity(requestBookDto, Book.class);
        // assert that mapping done successfully
        assertNotNull(book);
        assertEquals(book.getAuthor(), requestBookDto.getAuthor());
        assertEquals(book.getTitle(), requestBookDto.getTitle());
        assertEquals(book.getIsbn(), requestBookDto.getIsbn());
        assertEquals(book.getPublicationYear(), requestBookDto.getPublicationYear());
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

}
