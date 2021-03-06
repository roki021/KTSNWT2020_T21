package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class OfferTypeDTO {
    private Integer id;
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    private List<String> subtypes;

    private Integer subtypesNumber;

    public OfferTypeDTO(){

    }

    public OfferTypeDTO(Integer id, @NotBlank(message = "Name cannot be empty.") String name, List<String> subtypes){
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

    public Integer getSubtypesNumber() {
        return subtypesNumber;
    }

    public void setSubtypesNumber(Integer subtypesNumber) {
        this.subtypesNumber = subtypesNumber;
    }
}
