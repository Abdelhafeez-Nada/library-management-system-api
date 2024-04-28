package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.abdelhafeez.librarymanagementsystemapi.entity.Book;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.BookRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestBookDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponseBookDto;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BeanMapper beanMapper;

    @Override
    public List<ResponseBookDto> getAllBooks() throws Exception {
        return beanMapper.mapEntityListToDtoList(bookRepo.findAll(), ResponseBookDto.class);
    }

    @Override
    public ResponseBookDto getBookById(Long id) throws Exception, ResourceNotFoundException {
        Optional<Book> optional = bookRepo.findById(id);
        if (!optional.isPresent())
            throw new ResourceNotFoundException("Book", "Id", id);
        return beanMapper.mapEntityToDto(optional.get(), ResponseBookDto.class);
    }

    @Override
    public ResponseBookDto createBook(RequestBookDto dto) throws Exception, BadRequestException {
        Book saved = bookRepo.save(beanMapper.mapDtoToEntity(dto, Book.class));
        return beanMapper.mapEntityToDto(saved, ResponseBookDto.class);
    }

    @Override
    public ResponseBookDto updateBook(Long id, RequestBookDto dto)
            throws Exception, ResourceNotFoundException, BadRequestException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public void deleteBook(Long id) throws Exception, ResourceNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }
}
