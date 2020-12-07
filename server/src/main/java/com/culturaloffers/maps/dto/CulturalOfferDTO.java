package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CulturalOfferDTO {

    private Integer id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    //@NotBlank(message = "GeoLocationId can not be empty")
    private Integer geoLocationId;

    //@NotBlank(message = "SubTypeId can not be empty")
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

    public CulturalOfferDTO(
            Integer id,
            @NotBlank(message = "Title cannot be empty") String title,
            @NotBlank(message = "Description cannot be empty") String description,
            //Integer geoLocationId,
            //Integer subTypeId,
            List<String> imageUrls){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        //this.geoLocationId = geoLocationId;
        //this.subTypeId = subTypeId;
    }

    public CulturalOfferDTO() {
    }
}
