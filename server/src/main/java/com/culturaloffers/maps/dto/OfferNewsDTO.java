package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.OfferNews;

import java.text.SimpleDateFormat;
import java.util.List;

public class OfferNewsDTO {

    public Integer id;
    public String title;
    public String description;
    public List<String> imageUrls;
    public Integer culturalOfferId;
    public String date;

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
