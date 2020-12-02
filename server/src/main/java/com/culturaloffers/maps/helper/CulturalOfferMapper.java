package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.model.CulturalOffer;

import java.util.ArrayList;
import java.util.List;

public class CulturalOfferMapper implements MapperInterface<CulturalOffer, CulturalOfferDTO> {

    @Override
    public CulturalOffer toEntity(CulturalOfferDTO dto) {
        return new CulturalOffer(dto);
    }

    @Override
    public List<CulturalOffer> toEntityList(List<CulturalOfferDTO> dtoList) {
        List<CulturalOffer> ret = new ArrayList<>();
        for (CulturalOfferDTO dto: dtoList){
            CulturalOffer offer = toEntity(dto);
            ret.add(offer);
        }
        return ret;
    }

    @Override
    public CulturalOfferDTO toDto(CulturalOffer entity) {
        return new CulturalOfferDTO(entity);
    }

    @Override
    public List<CulturalOfferDTO> toDtoList(List<CulturalOffer> entityList) {
        List<CulturalOfferDTO> ret = new ArrayList<>();
        for (CulturalOffer offer: entityList){
            CulturalOfferDTO dto = toDto(offer);
            ret.add(dto);
        }
        return ret;
    }

}