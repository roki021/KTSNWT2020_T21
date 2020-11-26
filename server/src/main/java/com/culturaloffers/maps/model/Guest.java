package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Guest extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column
    private Boolean active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subscribers")
    private Set<CulturalOffer> subscriptions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Grade> offerGrades;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<CulturalOffer> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<CulturalOffer> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Grade> getOfferGrades() {
        return offerGrades;
    }

    public void setOfferGrades(Set<Grade> offerGrades) {
        this.offerGrades = offerGrades;
    }
}
