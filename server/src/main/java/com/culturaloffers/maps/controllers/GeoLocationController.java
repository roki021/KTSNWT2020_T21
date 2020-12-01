package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GeoLocationDTO;
import com.culturaloffers.maps.services.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, String>> addGeoLocation(@RequestBody GeoLocationDTO newGeoLocation) {
        Map<String, String> response = new HashMap<>();
        GeoLocationDTO addedGeoLocation = geoLocationService.insert(newGeoLocation);
        if(addedGeoLocation != null) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public Page<GeoLocationDTO> getGeoLocations(Pageable pageable) {
        return geoLocationService.getGeoLocations(pageable);
    }

    @GetMapping("/{coords}")
    public ResponseEntity<GeoLocationDTO> getGeoLocation(@PathVariable String coords) {

        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            Map<String, String> response = new HashMap<>();
            GeoLocationDTO geoLocationDTO = geoLocationService.getByLatitudeAndLongitude(latitude, longitude);
            return new ResponseEntity<>(geoLocationDTO, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{coords}")
    public ResponseEntity<GeoLocationDTO> updateGeoLocation(@PathVariable String coords,
                                                            @RequestBody GeoLocationDTO geoLocationDTO) {
        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            Map<String, String> response = new HashMap<>();
            GeoLocationDTO updatedGeoLocation = geoLocationService.update(latitude, longitude, geoLocationDTO);
            return new ResponseEntity<>(updatedGeoLocation, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{coords}")
    public ResponseEntity<Map<String, String>> deleteGeoLocation(@PathVariable String coords) {
        try {
            String[] splited = coords.split(",");
            Double latitude = Double.parseDouble(splited[0]);
            Double longitude = Double.parseDouble(splited[1]);
            Map<String, String> response = new HashMap<>();
            if(geoLocationService.delete(latitude, longitude)) {
                response.put("message", "true");
            }
            else {
                response.put("message", "false");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
