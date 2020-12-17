package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotNull;

public class ZoomDTO {

    @NotNull(message = "LatitudeUpperCorner cannot be empty.")
    private Double latitudeUpperCorner;

    @NotNull(message = "LongitudeUpperCorner cannot be empty.")
    private Double longitudeUpperCorner;

    @NotNull(message = "LatitudeLowerCorner cannot be empty.")
    private Double latitudeLowerCorner;

    @NotNull(message = "LongitudeLowerCorner cannot be empty.")
    private Double longitudeLowerCorner;

    public ZoomDTO() {}

    public ZoomDTO(
            @NotNull(message = "LatitudeUpperCorner cannot be empty.") Double latitudeUpperCorner,
            @NotNull(message = "LongitudeUpperCorner cannot be empty.") Double longitudeUpperCorner,
            @NotNull(message = "LatitudeLowerCorner cannot be empty.") Double latitudeLowerCorner,
            @NotNull(message = "LongitudeLowerCorner cannot be empty.") Double longitudeLowerCorner
    ) {
        this.latitudeUpperCorner = latitudeUpperCorner;
        this.longitudeUpperCorner = longitudeUpperCorner;
        this.latitudeLowerCorner = latitudeLowerCorner;
        this.longitudeLowerCorner = longitudeLowerCorner;
    }

    public Double getlatitudeUpperCorner() {
        return latitudeUpperCorner;
    }

    public void setlatitudeUpperCorner(Double latitudeUpperCorner) {
        this.latitudeUpperCorner = latitudeUpperCorner;
    }

    public Double getlongitudeUpperCorner() {
        return longitudeUpperCorner;
    }

    public void setlongitudeUpperCorner(Double longitudeUpperCorner) {
        this.longitudeUpperCorner = longitudeUpperCorner;
    }

    public Double getlatitudeLowerCorner() {
        return latitudeLowerCorner;
    }

    public void setlatitudeLowerCorner(Double latitudeLowerCorner) {
        this.latitudeLowerCorner = latitudeLowerCorner;
    }

    public Double getlongitudeLowerCorner() {
        return longitudeLowerCorner;
    }

    public void setlongitudeLowerCorner(Double longitudeLowerCorner) {
        this.longitudeLowerCorner = longitudeLowerCorner;
    }
}
