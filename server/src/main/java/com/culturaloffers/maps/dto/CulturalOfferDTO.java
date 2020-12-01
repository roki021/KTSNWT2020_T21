package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.CulturalOffer;

import java.util.List;

public class CulturalOfferDTO {

    public Integer id;
    public String title;
    public String description;
    public Integer geoLocationId;
    public Integer subTypeId;
    public List<String> imageUrls;

    public CulturalOfferDTO(CulturalOffer offer){
        this.id = offer.getId();
        this.description = offer.getDescription();
        //this.geoLocationId = offer.getGeoLocation().getId();
        //this.subTypeId = offer.getSubtype().getId();
        this.title = offer.getTitle();
        this.imageUrls = offer.getImageUrls();
    }

    public CulturalOfferDTO() {
    }
}
