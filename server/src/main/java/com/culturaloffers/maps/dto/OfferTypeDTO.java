package com.culturaloffers.maps.dto;

import java.util.List;

public class OfferTypeDTO {
    private Integer id;
    //@NotBlank(message = "Name cannot be empty.");
    private String name;
    private List<String> subtypes;

    public OfferTypeDTO(){

    }

    public OfferTypeDTO(Integer id, String name, List<String> subtypes){
        this.id = id;
        this.name = name;
        this.subtypes = subtypes;
    }

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

    public List<String> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<String> subtypes) {
        this.subtypes = subtypes;
    }
}
