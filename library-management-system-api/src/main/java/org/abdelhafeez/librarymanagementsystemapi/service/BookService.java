package org.abdelhafeez.librarymanagementsystemapi.service;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;

public interface BookService {

    public List<RequestBookDto> getAllBooks() throws Exception;

    public RequestBookDto getBookById(Long id) throws Exception, ResourceNotFoundException;

    public RequestBookDto createBook(RequestBookDto dto) throws Exception, BadRequestException;

}
