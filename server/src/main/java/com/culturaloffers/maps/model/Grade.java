package com.culturaloffers.maps.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Value can't be blank")
    @Min(1)
    @Max(10)
    private Integer value;

    @Column(nullable = false)
    @NotNull(message = "Date can't be blank")
    private Date gradedOn;

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
