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
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BeanMapper beanMapper;

    /**
     * Retrieves all books available in the system.
     * 
     * @return A list of ResponseBookDto representing all books.
     */
    @Override
    public List<ResponseBookDto> getAllBooks() {
        // Retrieve all books from the repository
        List<Book> allBooks = bookRepo.findAll();
        // Map the list of book entities to a list of DTOs and return it
        return beanMapper.mapEntityListToDtoList(allBooks, ResponseBookDto.class);
    }

    /**
     * Retrieves a book by its ID.
     * 
     * @param id The ID of the book to retrieve.
     * @return The ResponseBookDto representing the book.
     * @throws ResourceNotFoundException If the book with the given ID is not found.
     */
    @Override
    public ResponseBookDto getBookById(Long id) throws ResourceNotFoundException {
        // Retrieve book from the repository by ID
        Optional<Book> optional = bookRepo.findById(id);
        // Map the book to a DTO if it exists, otherwise throw a
        // ResourceNotFoundException
        return optional.map(
                book -> beanMapper.mapEntityToDto(book, ResponseBookDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Id", id));
    }

    /**
     * Creates a new book.
     * 
     * @param dto The RequestBookDto containing information about the book to be
     *            created.
     * @return The ResponseBookDto representing the newly created book.
     * @throws BadRequestException If the request is malformed or contains invalid
     *                             data.
     */
    @Override
    public ResponseBookDto createBook(RequestBookDto dto) throws BadRequestException {
        // Save the book entity to the repository
        Book saved = bookRepo.save(beanMapper.mapDtoToEntity(dto, Book.class));
        // Map the saved book entity to a DTO and return it
        return beanMapper.mapEntityToDto(saved, ResponseBookDto.class);
    }

    /**
     * Updates the details of a book with the specified ID.
     *
     * @param id  The ID of the book to be updated.
     * @param dto The RequestBookDto containing the updated information of the book.
     * @return The ResponseBookDto representing the updated book.
     * @throws ResourceNotFoundException If the book with the given ID is not found.
     * @throws BadRequestException       If the request is malformed or contains
     *                                   invalid data.
     */
    @Override
    @Transactional(rollbackFor = { ResourceNotFoundException.class, BadRequestException.class })
    public ResponseBookDto updateBook(Long id, RequestBookDto dto)
            throws ResourceNotFoundException, BadRequestException {
        // Input Validation
        if (id == null || dto == null)
            throw new BadRequestException("Invalid input parameters");
        // Check Existence of Entity
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (!optionalBook.isPresent())
            throw new ResourceNotFoundException("Book", "Id", id);
        // Retrieve the book entity from the repository
        Book book = optionalBook.get();
        // Update the book entity with the new information
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        // Save the changes to the repository
        Book updatedBook = bookRepo.save(book);
        // Map the updated book entity to a DTO and return it
        return beanMapper.mapEntityToDto(updatedBook, ResponseBookDto.class);
    }

    /**
     * Deletes a book with the specified ID.
     *
     * @param id The ID of the book to be deleted.
     * @throws ResourceNotFoundException If the book with the given ID is not found.
     * @throws BadRequestException       If the request is malformed or contains
     *                                   invalid data.
     */
    @Override
    public void deleteBook(Long id) throws ResourceNotFoundException, BadRequestException {
        // Input Validation
        if (id == null)
            throw new BadRequestException("Invalid input parameters");
        // Check Existence of Entity
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (!optionalBook.isPresent())
            throw new ResourceNotFoundException("Book", "Id", id);
        // Delete the book entity from the repository
        bookRepo.deleteById(id);
    }

}
