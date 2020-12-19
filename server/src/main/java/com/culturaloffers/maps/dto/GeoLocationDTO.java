package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GeoLocationDTO {

    @NotNull(message = "Latitude cannot be empty.")
    private Double latitude;

    @NotNull(message = "Longitude cannot be empty.")
    private Double longitude;

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    public GeoLocationDTO() {}

    public GeoLocationDTO(
            @NotNull(message = "Latitude cannot be empty.") Double latitude,
            @NotNull(message = "Longitude cannot be empty.") Double longitude,
            @NotBlank(message = "Address cannot be empty.") String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
