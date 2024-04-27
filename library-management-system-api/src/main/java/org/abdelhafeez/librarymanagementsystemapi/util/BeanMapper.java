package org.abdelhafeez.librarymanagementsystemapi.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BeanMapper {

    private final ModelMapper mapper;
}
