package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.CulturalOffer;

import java.util.List;

public class CulturalOfferDTO {

    private Integer id;
    private String title;
    private String description;
    private Integer geoLocationId;
    private Integer subTypeId;
    private List<String> imageUrls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGeoLocationId() {
        return geoLocationId;
    }

    public void setGeoLocationId(Integer geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public Integer getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(Integer subTypeId) {
        this.subTypeId = subTypeId;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

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
