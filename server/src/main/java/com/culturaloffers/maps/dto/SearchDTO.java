package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;

public class SearchDTO {
    @NotBlank(message = "Search value cannot be empty.")
    private String searchValue;
    @NotBlank(message = "Search field cannot be empty.")
    private String searchField;

    public SearchDTO(){

    }
    public SearchDTO(
            @NotBlank(message = "Search value cannot be empty.") String searchValue,
            @NotBlank(message = "Search field cannot be empty.") String searchField
    ){
        this.searchValue = searchValue;
        this.searchField = searchField;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
