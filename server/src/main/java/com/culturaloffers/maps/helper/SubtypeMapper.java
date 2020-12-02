package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.SubtypeDTO;
import com.culturaloffers.maps.model.Subtype;

import java.util.ArrayList;
import java.util.List;

public class SubtypeMapper implements MapperInterface<Subtype, SubtypeDTO> {
    @Override
    public Subtype toEntity(SubtypeDTO dto) {
        Subtype subtype = new Subtype();
        subtype.setName(dto.getName());
        return subtype;
    }

    @Override
    public List<Subtype> toEntityList(List<SubtypeDTO> dtoList) {
        return null;
    }

    @Override
    public SubtypeDTO toDto(Subtype entity) {
        SubtypeDTO subtypeDTO = new SubtypeDTO(entity.getId(), entity.getName(), entity.getOfferType().getName());
        return subtypeDTO;
    }

    @Override
    public List<SubtypeDTO> toDtoList(List<Subtype> entityList) {
        List<SubtypeDTO> subtypeDTOS = new ArrayList<SubtypeDTO>();
        for(Subtype subtype : entityList){
            SubtypeDTO subtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());
            subtypeDTOS.add(subtypeDTO);
        }
        return subtypeDTOS;
    }
}
