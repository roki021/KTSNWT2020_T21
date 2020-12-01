package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.Grade;

import java.util.Date;

public class GradeDTO {
    private Integer id;
    private int value;
    private Date gradedOn;
    private int culturalOfferId;
    private String culturalOfferName;
    private int userId;
    private String userUsername;

    public GradeDTO(Grade grade)
    {
        this.id = grade.getId();
        this.value = grade.getValue();
        this.gradedOn = grade.getGradedOn();
        this.culturalOfferId = grade.getCulturalOffer().getId();
        this.culturalOfferName = grade.getCulturalOffer().getTitle();
        this.userId = grade.getUser().getId();
        this.userUsername = grade.getUser().getUsername();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getGradedOn() {
        return gradedOn;
    }

    public void setGradedOn(Date gradedOn) {
        this.gradedOn = gradedOn;
    }

    public int getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(int culturalOfferId) {
        this.culturalOfferId = culturalOfferId;
    }

    public String getCulturalOfferName() {
        return culturalOfferName;
    }

    public void setCulturalOfferName(String culturalOfferName) {
        this.culturalOfferName = culturalOfferName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
