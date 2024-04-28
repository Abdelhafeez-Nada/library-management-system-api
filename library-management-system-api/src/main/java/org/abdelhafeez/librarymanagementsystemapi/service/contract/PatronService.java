package org.abdelhafeez.librarymanagementsystemapi.service.contract;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.exception.ResourceNotFoundException;
import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;
import org.springframework.data.domain.Page;

public interface PatronService {

    public List<ResponsePatronDto> getAllPatrons();

    public Page<ResponsePatronDto> getAllPatrons(int page, int size);

    public ResponsePatronDto getPatronById(Long id) throws ResourceNotFoundException;
}
