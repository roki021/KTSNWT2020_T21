package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.GeoLocationDTO;
import com.culturaloffers.maps.model.GeoLocation;

import java.util.List;
import java.util.stream.Collectors;

public class GeoLocationMapper implements MapperInterface<GeoLocation, GeoLocationDTO> {

    @Override
    public GeoLocation toEntity(GeoLocationDTO dto) {
        return new GeoLocation(
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getAddress()
        );
    }

    @Override
    public List<GeoLocation> toEntityList(List<GeoLocationDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public GeoLocationDTO toDto(GeoLocation entity) {
        return new GeoLocationDTO(
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAddress()
        );
    }

    @Override
    public List<GeoLocationDTO> toDtoList(List<GeoLocation> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
