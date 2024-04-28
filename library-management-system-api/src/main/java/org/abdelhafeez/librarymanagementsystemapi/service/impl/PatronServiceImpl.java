package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.BadRequestException;
import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.service.contract.PatronService;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.RequestPatronDto;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.springframework.data.domain.Page;

public class PatronServiceImpl implements PatronService {

    @Override
    public List<ResponsePatronDto> getAllPatrons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPatrons'");
    }

    @Override
    public Page<ResponsePatronDto> getAllPatrons(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPatrons'");
    }

    @Override
    public ResponsePatronDto getPatronById(Long id) throws ResourceNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatronById'");
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
