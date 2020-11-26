package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date commentedOn;

    @ElementCollection
    @CollectionTable(name="commentImages")
    private List<String> imageUrls;

    @ManyToOne
    private Guest user;

    @ManyToOne
    private CulturalOffer culturalOffer;

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

    public Guest getUser() {
        return user;
    }

    public void setUser(Guest user) {
        this.user = user;
    }

    public CulturalOffer getCulturalOffer() {
        return culturalOffer;
    }

    public void setCulturalOffer(CulturalOffer culturalOffer) {
        this.culturalOffer = culturalOffer;
    }
}
