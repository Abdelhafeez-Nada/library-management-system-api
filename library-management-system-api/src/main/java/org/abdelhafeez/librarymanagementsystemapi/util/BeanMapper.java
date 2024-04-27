package org.abdelhafeez.librarymanagementsystemapi.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BeanMapper {

    private final ModelMapper mapper;

    public <T, D> D mapEntityToDto(T entity, Class<D> dtoClass) {
        return mapper.map(entity, dtoClass);
    }

    public <T, D> T mapDtoToEntity(D dto, Class<T> entityClass) {
        return mapper.map(dto, entityClass);
    }

    public <T, D> List<D> mapEntityListToDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> mapEntityToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
