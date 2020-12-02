package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfferTypeMapper implements MapperInterface<OfferType, OfferTypeDTO> {

    @Override
    public OfferType toEntity(OfferTypeDTO dto) {
        OfferType offerType = new OfferType();
        offerType.setName(dto.getName());

        //offerType = offerTypeService.create(offerType);
        Set<Subtype> subtypes = new HashSet<Subtype>();
        if(dto.getSubtypes() != null){
            for(String type : dto.getSubtypes()){
                Subtype subtype = new Subtype();
                subtype.setName(type);
                subtype.setOfferType(offerType);
                subtypes.add(subtype);
            }
        }
        offerType.setSubtypes(subtypes);
        return offerType;
    }

    @Override
    public List<OfferType> toEntityList(List<OfferTypeDTO> dtoList) {
        return null;
    }

    @Override
    public OfferTypeDTO toDto(OfferType entity) {
        List<String> subtypes = new ArrayList<String>();
        for(Subtype subtype : entity.getSubtypes()){
            subtypes.add(subtype.getName());
        }

        return new OfferTypeDTO(entity.getId(), entity.getName(), subtypes);
    }

    @Override
    public List<OfferTypeDTO> toDtoList(List<OfferType> entityList) {
        List<String> subtypes = new ArrayList<String>();
        List<OfferTypeDTO> offerTypeDTOS = new ArrayList<OfferTypeDTO>();
        for(OfferType offerType : entityList){
            for(Subtype subtype : offerType.getSubtypes()){
                subtypes.add(subtype.getName());
            }
            OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);
            offerTypeDTOS.add(dto);
            subtypes = new ArrayList<String>();

        }
        return offerTypeDTOS;
    }
}
