package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class OfferType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "offerType")
    private Set<Subtype> subtypes;

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

    public Set<Subtype> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(Set<Subtype> subtypes) {
        this.subtypes = subtypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfferType offerType = (OfferType) o;
        if (offerType.getId() == null || id == null) {
            if(offerType.getName().equals(getName())){
                return true;
            }
            return false;
        }
        return Objects.equals(id, offerType.getId());
    }
}
