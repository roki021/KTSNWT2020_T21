package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Guest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class CommentDTO {

    private Integer id;

    @NotBlank(message = "Content can't be blank")
    private String content;

    @NotNull(message = "Date can't be blank")
    private Date commentedOn;

    private List<String> imageUrls;
    private Integer userId;
    private Integer culturalOfferId;
    private String userUsername;
    private String culturalOfferName;

    public CommentDTO(Comment comment)
    {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commentedOn = comment.getCommentedOn();
        this.imageUrls = comment.getImageUrls();
        this.userId = comment.getUser().getId();
        this.culturalOfferId = comment.getCulturalOffer().getId();
        this.userUsername = comment.getUser().getUsername();
        this.culturalOfferName = comment.getCulturalOffer().getTitle();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(Integer culturalOfferId) {
        this.culturalOfferId = culturalOfferId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getCulturalOfferName() {
        return culturalOfferName;
    }

    public void setCulturalOfferName(String culturalOfferName) {
        this.culturalOfferName = culturalOfferName;
    }
}
