package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.model.Guest;

import java.util.List;
import java.util.stream.Collectors;

public class GuestMapper implements MapperInterface<Guest, GuestDTO> {

    @Override
    public Guest toEntity(GuestDTO dto) {
        return new Guest(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmailAddress(),
                dto.getUsername(),
                dto.getPassword()
        );
    }

    @Override
    public List<Guest> toEntityList(List<GuestDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public GuestDTO toDto(Guest entity) {
        return new GuestDTO(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailAddress(),
                entity.getUsername(),
                entity.getPassword()
        );
    }

    @Override
    public List<GuestDTO> toDtoList(List<Guest> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
