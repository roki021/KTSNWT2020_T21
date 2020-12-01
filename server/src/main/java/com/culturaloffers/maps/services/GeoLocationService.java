package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.GeoLocationDTO;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.repositories.GeoLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeoLocationService {

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    public GeoLocation getByAddress(String address) {
        return geoLocationRepository.findByAddress(address);
    }

    public GeoLocation getByLatitudeAndLongitude(Double latitude, Double longitude) {
        return geoLocationRepository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public Page<GeoLocation> getGeoLocations(Pageable pageable) {
        return geoLocationRepository.findAll(pageable);
    }

    public boolean delete(String address) {
        GeoLocation geoLocation = geoLocationRepository.findByAddress(address);

        if(geoLocation != null) {
            geoLocationRepository.delete(geoLocation);

            return true;
        }

        return false;
    }

    public boolean delete(Double latitude, Double longitude) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(latitude, longitude);

        if(geoLocation != null) {
            geoLocationRepository.delete(geoLocation);

            return true;
        }

        return false;
    }

    public GeoLocation insert(GeoLocation newGeoLocation) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(
                newGeoLocation.getLatitude(),
                newGeoLocation.getLongitude());

        if(geoLocation == null)
            return geoLocationRepository.save(newGeoLocation);

        return null;
    }

    public GeoLocation update(Double latitude, Double longitude, GeoLocation newGeoLocation) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(
                latitude,
                longitude);

        if(geoLocation != null) {
            geoLocation.setLatitude(newGeoLocation.getLatitude());
            geoLocation.setLongitude(newGeoLocation.getLongitude());
            geoLocation.setAddress(newGeoLocation.getAddress());

            return geoLocationRepository.save(geoLocation);
        }

        return null;
    }
}
