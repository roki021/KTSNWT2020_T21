package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.model.Grade;

import java.util.List;
import java.util.stream.Collectors;

public class GradeMapper implements MapperInterface<Grade, GradeDTO>{

    @Override
    public Grade toEntity(GradeDTO dto) {
        return null;
    }

    @Override
    public List<Grade> toEntityList(List<GradeDTO> dtoList) {
        return null;
    }

    @Override
    public GradeDTO toDto(Grade entity) {
        return new GradeDTO(entity, entity.getValue(), entity.getGradedOn());
    }

    @Override
    public List<GradeDTO> toDtoList(List<Grade> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
