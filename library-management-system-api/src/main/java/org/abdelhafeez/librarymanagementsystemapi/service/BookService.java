package org.abdelhafeez.librarymanagementsystemapi.service;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.web.dto.BookDto;

public interface BookService {

    public List<BookDto> getAllBooks() throws Exception;

}
