package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.dto.SearchDTO;
import com.culturaloffers.maps.dto.ZoomDTO;
import com.culturaloffers.maps.helper.CulturalOfferMapper;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/offers")
public class CulturalOfferController {

    @Autowired
    private CulturalOfferService service;

    @Autowired
    private GeoLocationService geoLocationService;

    private CulturalOfferMapper mapper = new CulturalOfferMapper();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> addCulturalOffer(@RequestBody CulturalOfferDTO dto){
        CulturalOffer culturalOffer = mapper.toEntity(dto);
        try {
            GeoLocation geo = new GeoLocation(dto.getLatitude(), dto.getLongitude(), dto.getAddress());
            geoLocationService.insert(geo);
            service.create(culturalOffer, dto.getAddress(), dto.getSubTypeName());
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.toDto(culturalOffer), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CulturalOfferDTO>> getAll(){
        List<CulturalOfferDTO> ret = mapper.toDtoList(service.findAll());
        for (CulturalOffer offer: service.findAll()){
            System.out.println("offer "+offer.getId());
            for (OfferNews news: offer.getOfferNews())
                System.out.println("\t"+news.getTitle()+"-"+news.getDescription());
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(value="/by-page", method = RequestMethod.GET)
    public ResponseEntity<List<CulturalOfferDTO>> getAllPageable(Pageable pageable){
        Page<CulturalOffer> page = service.findAll(pageable);
        List<CulturalOfferDTO> dtos = mapper.toDtoList(page.getContent());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CulturalOfferDTO> findCulturalOffer(@PathVariable Integer id){
        CulturalOffer offer = service.findOne(id);
        if (offer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapper.toDto(offer), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> updateOffer(@RequestBody CulturalOfferDTO dto, @PathVariable Integer id){
        CulturalOffer culturalOffer = mapper.toEntity(dto);
        try{
            culturalOffer.setGeoLocation(service.findOne(id).getGeoLocation());
            culturalOffer.setSubtype(service.findOne(id).getSubtype());
            if (culturalOffer.getDescription().isBlank())
                culturalOffer.setDescription(service.findOne(id).getDescription());
            if (culturalOffer.getTitle().isBlank())
                culturalOffer.setTitle(service.findOne(id).getTitle());
            service.update(id, culturalOffer);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.toDto(culturalOffer), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOffer(@PathVariable Integer id){
        try {
            service.delete(id);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CulturalOfferDTO>> searchCulturalOffers(@Valid @RequestBody SearchDTO dto){
        List<CulturalOffer> culturalOffers = null;
        try {
            culturalOffers = service.search(dto.getSearchValue(), dto.getSearchField());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.toDtoList(culturalOffers), HttpStatus.OK);
    }
    // Accessible by all users
    @RequestMapping(value="/filtering", method = RequestMethod.POST)
    public ResponseEntity<List<CulturalOfferDTO>> getAllInCurrentZoom(@Valid @RequestBody ZoomDTO zoom) {
        try {
            return ResponseEntity.ok(mapper.toDtoList(service.getAllInCurrentZoom(zoom)));
        } catch(ValidationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
