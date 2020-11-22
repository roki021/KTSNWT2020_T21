package com.culturaloffers.maps.model;

import javax.persistence.*;
import java.util.Set;

public class OfferType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}
