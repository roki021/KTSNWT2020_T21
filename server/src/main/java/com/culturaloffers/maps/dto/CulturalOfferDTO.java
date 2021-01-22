package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CulturalOfferDTO {

    private Integer id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Address can not be empty")
    private String address;

    @NotBlank(message = "SubTypeName can not be empty")
    private String subTypeName;

    private String offerType;

    private List<String> imageUrls;

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public CulturalOfferDTO(
            Integer id,
            @NotBlank(message = "Title cannot be empty") String title,
            @NotBlank(message = "Description cannot be empty") String description,
            @NotBlank(message = "Address can not be empty") String address,
            @NotBlank(message = "SubTypeName can not be empty") String subTypeName,
            List<String> imageUrls){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.address = address;
        this.subTypeName = subTypeName;
    }

    public CulturalOfferDTO(
            Integer id,
            @NotBlank(message = "Title cannot be empty") String title,
            @NotBlank(message = "Description cannot be empty") String description,
            @NotBlank(message = "Address can not be empty") String address,
            @NotBlank(message = "SubTypeName can not be empty") String subTypeName,
            String offerType,
            List<String> imageUrls,
            Double longitude,
            Double latitude){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.address = address;
        this.subTypeName = subTypeName;
        this.offerType = offerType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CulturalOfferDTO() {
    }
}
