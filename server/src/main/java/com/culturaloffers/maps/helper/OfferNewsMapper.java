package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.OfferNewsDTO;
import com.culturaloffers.maps.model.OfferNews;

import java.util.ArrayList;
import java.util.List;

public class OfferNewsMapper implements MapperInterface<OfferNews, OfferNewsDTO> {

    @Override
    public OfferNews toEntity(OfferNewsDTO dto) {
        return new OfferNews(dto);
    }

    @Override
    public List<OfferNews> toEntityList(List<OfferNewsDTO> dtoList) {
        List<OfferNews> ret = new ArrayList<>();
        for (OfferNewsDTO dto: dtoList){
            OfferNews news = toEntity(dto);
            ret.add(news);
        }
        return ret;
    }

    @Override
    public OfferNewsDTO toDto(OfferNews entity) {
        return new OfferNewsDTO(entity);
    }

    @Override
    public List<OfferNewsDTO> toDtoList(List<OfferNews> entityList) {
        List<OfferNewsDTO> ret = new ArrayList<>();
        for (OfferNews news: entityList){
            OfferNewsDTO dto = toDto(news);
            ret.add(dto);
        }
        return ret;
    }
}
