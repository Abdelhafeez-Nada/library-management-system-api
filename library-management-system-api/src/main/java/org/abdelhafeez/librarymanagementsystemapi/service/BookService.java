package org.abdelhafeez.librarymanagementsystemapi.service;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.BookDto;

public interface BookService {

    public List<BookDto> getAllBooks() throws Exception;

    public BookDto getBookById(Long id) throws Exception, ResourceNotFoundException;

    public BookDto createBook(BookDto dto) throws Exception, BadRequestException;

}
