package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class CulturalOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private List<String> imageUrls;

    @ManyToOne
    private GeoLocation geoLocation;

    @ManyToOne
    private Subtype subtype;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "offer")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "offer")
    private Set<Grade> userGrades;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OfferNews> offerNews;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Guest> subscribers;

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

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Grade> getUserGrades() {
        return userGrades;
    }

    public void setUserGrades(Set<Grade> userGrades) {
        this.userGrades = userGrades;
    }

    public Set<OfferNews> getOfferNews() {
        return offerNews;
    }

    public void setOfferNews(Set<OfferNews> offerNews) {
        this.offerNews = offerNews;
    }

    public Set<Guest> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Guest> subscribers) {
        this.subscribers = subscribers;
    }
}
