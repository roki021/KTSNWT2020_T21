package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.GeoLocation;

public class GeoLocationDTO {
    private Double latitude;
    private Double longitude;
    private String address;

    public GeoLocationDTO() {}

    public GeoLocationDTO(GeoLocation geoLocation) {
        if(geoLocation != null) {
            this.latitude = geoLocation.getLatitude();
            this.longitude = geoLocation.getLongitude();
            this.address = geoLocation.getAddress();
        }
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
