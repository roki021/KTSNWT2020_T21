package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private OfferType offerType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subtype")
    private Set<CulturalOffer> culturalOffers;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public Set<CulturalOffer> getCulturalOffers() {
        return culturalOffers;
    }

    public void setCulturalOffers(Set<CulturalOffer> culturalOffers) {
        this.culturalOffers = culturalOffers;
    }
}
