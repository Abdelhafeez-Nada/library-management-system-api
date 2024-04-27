package org.abdelhafeez.librarymanagementsystemapi.service;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;

public interface BookService {

    public List<ResponseBookDto> getAllBooks() throws Exception;

    public ResponseBookDto getBookById(Long id) throws Exception, ResourceNotFoundException;

    public ResponseBookDto createBook(RequestBookDto dto) throws Exception, BadRequestException;

    public ResponseBookDto updateBook(Long id, RequestBookDto dto)
            throws Exception, ResourceNotFoundException, BadRequestException;

}
