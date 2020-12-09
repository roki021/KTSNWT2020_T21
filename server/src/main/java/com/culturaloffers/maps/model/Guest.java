package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Guest extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subscribers")
    private Set<CulturalOffer> subscriptions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Grade> offerGrades;

    public Guest() {}

    public Guest(String firstName, String lastName, String emailAddress, String username, String password) {
        super(username, password, emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
    }

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
