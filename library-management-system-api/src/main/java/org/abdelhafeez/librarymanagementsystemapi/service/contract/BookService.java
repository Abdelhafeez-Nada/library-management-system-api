package org.abdelhafeez.librarymanagementsystemapi.service.contract;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.springframework.data.domain.Page;

public interface BookService {

    public List<ResponseBookDto> getAllBooks();

    public Page<ResponseBookDto> getAllBooks(int page, int size);

    public ResponseBookDto getBookById(Long id) throws ResourceNotFoundException;

    public ResponseBookDto createBook(RequestBookDto dto) throws BadRequestException;

    public ResponseBookDto updateBook(Long id, RequestBookDto dto)
            throws ResourceNotFoundException, BadRequestException;

    public void deleteBook(Long id) throws ResourceNotFoundException, BadRequestException;

    public void makeBookUnAvailable(Long id) throws ResourceNotFoundException, BadRequestException;

}
