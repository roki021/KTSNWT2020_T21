package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GeoLocationDTO;
import com.culturaloffers.maps.helper.GeoLocationMapper;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.services.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/geo-locations")
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    private GeoLocationMapper geoLocationMapper;

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<GeoLocationDTO> addGeoLocation(@RequestBody GeoLocationDTO newGeoLocation) {
        GeoLocation addedGeoLocation;
        try {
            addedGeoLocation = geoLocationService.insert(geoLocationMapper.toEntity(newGeoLocation));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(geoLocationMapper.toDto(addedGeoLocation), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<GeoLocationDTO> getGeoLocations(Pageable pageable) {
        Page<GeoLocation> users = geoLocationService.getGeoLocations(pageable);
        return new PageImpl<GeoLocationDTO>(
                geoLocationMapper.toDtoList(users.getContent()),
                pageable,
                users.getTotalElements()
        );
    }

    @GetMapping("/{coords}")
    public ResponseEntity<GeoLocationDTO> getGeoLocation(@PathVariable String coords) {
        GeoLocation geoLocation;
        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            geoLocation = geoLocationService.getByLatitudeAndLongitude(latitude, longitude);
            if(geoLocation == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(geoLocationMapper.toDto(geoLocation), HttpStatus.OK);
    }

    @PutMapping("/{coords}")
    public ResponseEntity<GeoLocationDTO> updateGeoLocation(@PathVariable String coords,
                                                            @RequestBody GeoLocationDTO geoLocationDTO) {
        GeoLocation geoLocation;
        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            geoLocation = geoLocationService.update(latitude, longitude, geoLocationMapper.toEntity(geoLocationDTO));
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(geoLocationMapper.toDto(geoLocation), HttpStatus.OK);
    }

    @DeleteMapping("/{coords}")
    public ResponseEntity<Void> deleteGeoLocation(@PathVariable String coords) {
        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            geoLocationService.delete(latitude, longitude);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public GeoLocationController() {
        this.geoLocationMapper = new GeoLocationMapper();
    }
}
