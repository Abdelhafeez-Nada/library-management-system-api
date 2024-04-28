package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.abdelhafeez.librarymanagementsystemapi.entity.Patron;
import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.repo.PatronRepo;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.abdelhafeez.librarymanagementsystemapi.util.BeanMapper;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestPatronDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepo patronRepo;

    private final BeanMapper beanMapper;

    /**
     * Retrieves all patrons available in the system.
     * 
     * @return A list of ResponsePatronDto representing all patrons.
     */
    @Override
    public List<ResponsePatronDto> getAllPatrons() {
        // Retrieve all patrons from the repository
        List<Patron> allPatrons = patronRepo.findAll();
        // Map the list of patron entities to a list of DTOs and return it
        return beanMapper.mapEntityListToDtoList(allPatrons, ResponsePatronDto.class);
    }

    /**
     * Retrieves a page of patrons.
     * 
     * @param page the page number (zero-based) to retrieve
     * @param size the size of the page to retrieve
     * @return a {@link org.springframework.data.domain.Page} of
     *         {@link ResponsePatronDto} objects representing the patrons in the
     *         specified page
     */
    @Override
    public Page<ResponsePatronDto> getAllPatrons(int page, int size) {
        // Create a Pageable object specifying the page number and size
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve a page of Patron entities from the repository based on the provided
        // pagination information
        Page<Patron> patronPage = patronRepo.findAll(pageable);
        // Map each Patron entity to a ResponsePatronDto
        List<ResponsePatronDto> dtoList = beanMapper.mapEntityListToDtoList(patronPage.getContent(),
                ResponsePatronDto.class);
        // Create and return page of ResponsePatronDto
        return new PageImpl<ResponsePatronDto>(dtoList, pageable, patronPage.getTotalElements());
    }

    /**
     * Retrieves a patron by their ID.
     * 
     * @param id The ID of the patron to retrieve.
     * @return The ResponsePatronDto representing the patron.
     * @throws ResourceNotFoundException If the patron with the given ID is not
     *                                   found.
     */
    @Override
    public ResponsePatronDto getPatronById(Long id) throws ResourceNotFoundException {
        // Retrieve patron from the repository by ID
        Optional<Patron> optional = patronRepo.findById(id);
        // Map the patron to a DTO if it exists, otherwise throw a
        // ResourceNotFoundException
        return optional.map(
                patron -> beanMapper.mapEntityToDto(patron, ResponsePatronDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "Id", id));
    }

    @Override
    public ResponsePatronDto createPatron(RequestPatronDto dto) throws BadRequestException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPatron'");
    }

    @Override
    public ResponsePatronDto updatePatron(Long id, RequestPatronDto dto)
            throws ResourceNotFoundException, BadRequestException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePatron'");
    }

    @Override
    public void deletePatron(Long id) throws ResourceNotFoundException, BadRequestException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePatron'");
    }

}
