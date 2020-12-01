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

    private List<GeoLocationDTO> convertToList(List<GeoLocation> geoLocations) {
        List<GeoLocationDTO> guestsDTO = new ArrayList<GeoLocationDTO>();
        for(GeoLocation guest : geoLocations) {
            guestsDTO.add(new GeoLocationDTO(guest));
        }

        return guestsDTO;
    }

    public GeoLocationDTO getByAddress(String address) {
        GeoLocation geoLocation = geoLocationRepository.findByAddress(address);
        return new GeoLocationDTO(geoLocation);
    }

    public GeoLocationDTO getByLatitudeAndLongitude(Double latitude, Double longitude) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(latitude, longitude);
        return new GeoLocationDTO(geoLocation);
    }

    public Page<GeoLocationDTO> getGeoLocations(Pageable pageable) {
        Page<GeoLocation> geoLocations = geoLocationRepository.findAll(pageable);
        return new PageImpl<GeoLocationDTO>(
                convertToList(geoLocations.getContent()),
                pageable,
                geoLocations.getTotalElements()
        );
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

    public GeoLocationDTO insert(GeoLocationDTO geoLocationDTO) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(
                geoLocationDTO.getLatitude(),
                geoLocationDTO.getLongitude());

        if(geoLocation == null) {
            geoLocation = new GeoLocation();
            geoLocation.setLatitude(geoLocationDTO.getLatitude());
            geoLocation.setLongitude(geoLocationDTO.getLongitude());
            geoLocation.setAddress(geoLocationDTO.getAddress());

            return new GeoLocationDTO(geoLocationRepository.save(geoLocation));
        }

        return null;
    }

    public GeoLocationDTO update(Double latitude, Double longitude, GeoLocationDTO geoLocationDTO) {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(
                latitude,
                longitude);

        if(geoLocation != null) {
            geoLocation.setLatitude(geoLocationDTO.getLatitude());
            geoLocation.setLongitude(geoLocationDTO.getLongitude());
            geoLocation.setAddress(geoLocationDTO.getAddress());

            return new GeoLocationDTO(geoLocationRepository.save(geoLocation));
        }

        return null;
    }
}
