package com.culturaloffers.maps.model;

import javax.persistence.*;

public class Subtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private OfferType offerType;
}
