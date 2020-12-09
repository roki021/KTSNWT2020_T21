package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;

public class SubtypeDTO {
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    private Integer id;
    private String offerTypeName;

    public SubtypeDTO(){

    }

    public SubtypeDTO(Integer id, @NotBlank(message = "Name cannot be empty.") String name, String offerTypeName){
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
