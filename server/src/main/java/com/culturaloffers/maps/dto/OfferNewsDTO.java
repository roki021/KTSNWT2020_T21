package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OfferNewsDTO {

    private Integer id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private List<String> imageUrls;

    @NotNull(message = "CulturalOfferId cannot be empty")
    private Integer culturalOfferId;

    private String date;

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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(Integer culturalOfferId) {
        this.culturalOfferId = culturalOfferId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OfferNewsDTO(
            Integer id,
            @NotBlank(message = "Title cannot be empty") String title,
            @NotBlank(message = "Description cannot be empty") String description,
            List<String> imageUrls,
            @NotNull(message = "CulturalOfferId cannot be empty") Integer culturalOfferId,
            Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.culturalOfferId = culturalOfferId;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.date = sdf.format(date);
    }

    public OfferNewsDTO(){}

}
