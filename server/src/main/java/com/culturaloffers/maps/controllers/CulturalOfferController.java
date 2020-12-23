package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.helper.CulturalOfferMapper;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.services.CulturalOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/offers")
public class CulturalOfferController {

    @Autowired
    private CulturalOfferService service;

    private CulturalOfferMapper mapper = new CulturalOfferMapper();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> addCulturalOffer(@RequestBody CulturalOfferDTO dto){
        CulturalOffer culturalOffer = mapper.toEntity(dto);
        try {
            service.create(culturalOffer, dto.getAddress(), dto.getSubTypeName());
        } catch (Exception exception){
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
    public ResponseEntity<Page<CulturalOfferDTO>> getAllPageable(Pageable pageable){
        Page<CulturalOffer> page = service.findAll(pageable);
        List<CulturalOfferDTO> dtos = mapper.toDtoList(page.toList());
        Page<CulturalOfferDTO> ret = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
        return new ResponseEntity<>(ret, HttpStatus.OK);
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
            service.update(id, mapper.toEntity(dto));
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

}
