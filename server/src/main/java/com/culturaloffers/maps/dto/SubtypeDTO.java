package com.culturaloffers.maps.dto;

public class SubtypeDTO {
    private String name;
    private Integer id;
    private String offerTypeName;

    public SubtypeDTO(){

    }

    public SubtypeDTO(Integer id, String name, String offerTypeName){
        this.id = id;
        this.name = name;
        this.offerTypeName = offerTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfferTypeName() {
        return offerTypeName;
    }

    public void setOfferTypeName(String offerTypeName) {
        this.offerTypeName = offerTypeName;
    }
}
