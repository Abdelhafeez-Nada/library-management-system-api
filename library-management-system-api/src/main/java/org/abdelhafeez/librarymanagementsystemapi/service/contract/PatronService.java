package org.abdelhafeez.librarymanagementsystemapi.service.contract;

import java.util.List;

import org.abdelhafeez.librarymanagementsystemapi.web.dto.ResponsePatronDto;

public interface PatronService {

    public List<ResponsePatronDto> getAllPatrons();
}
