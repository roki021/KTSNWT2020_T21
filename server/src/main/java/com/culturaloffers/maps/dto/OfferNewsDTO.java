package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.OfferNews;

import java.text.SimpleDateFormat;
import java.util.List;

public class OfferNewsDTO {

    private Integer id;
    private String title;
    private String description;
    private List<String> imageUrls;
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

    public OfferNewsDTO(OfferNews news){
        this.culturalOfferId = news.getCulturalOffer().getId();
        this.description = news.getDescription();
        this.id = news.getId();
        this.imageUrls = news.getImageUrls();
        this.title = news.getTitle();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.date = sdf.format(news.getDate());
    }

    public OfferNewsDTO(){}

}
