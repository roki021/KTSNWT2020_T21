package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.helper.OfferTypeMapper;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.services.OfferTypeService;
import com.culturaloffers.maps.services.SubtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/offer-types")
public class OfferTypeController {
    @Autowired
    private OfferTypeService offerTypeService;

    @Autowired
    private SubtypeService subtypeService;

    private OfferTypeMapper offerTypeMapper;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OfferTypeDTO>> getAllOfferTypes() {
        List<OfferType> offerTypes = offerTypeService.findAll();

        return new ResponseEntity<>(offerTypeMapper.toDtoList(offerTypes), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value= "/by-page", method = RequestMethod.GET)
    public ResponseEntity<List<OfferTypeDTO>> getAllOfferTypes(Pageable pageable) {
        Page<OfferType> offerTypesPage = offerTypeService.findAll(pageable);
        HttpHeaders header = new HttpHeaders();
        header.add("Total-pages", Long.toString(offerTypesPage.getTotalPages()));
        /*return new ResponseEntity<>(new PageImpl<>(offerTypeMapper.toDtoList(offerTypesPage.toList()),
                offerTypesPage.getPageable(),offerTypesPage.getTotalElements()), HttpStatus.OK);*/
        return new ResponseEntity<>(offerTypeMapper.toDtoList(offerTypesPage.toList()), header, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<OfferTypeDTO> getOfferType(@PathVariable Integer id){
        OfferType offerType = offerTypeService.findOne(id);
        if(offerType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offerTypeMapper.toDto(offerType), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferTypeDTO> createOfferType(@Valid @RequestBody OfferTypeDTO offerTypeDTO){
        OfferType offerType;
        try {

            offerType = offerTypeMapper.toEntity(offerTypeDTO);
            offerType = offerTypeService.create(offerType);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(offerTypeMapper.toDto(offerType), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferTypeDTO> updateOfferType(
            @Valid @RequestBody OfferTypeDTO offerTypeDTO, @PathVariable Integer id){
        OfferType offerType;
        try {
            offerType = offerTypeMapper.toEntity(offerTypeDTO);
            offerType = offerTypeService.update(offerType, id);
        } catch (Exception e) {
            if(e.getMessage() != null) {
                if (e.getMessage().equals("Offer type with given id doesn't exist"))
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(offerTypeMapper.toDto(offerType), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOfferType(@PathVariable Integer id){
        try {
            offerTypeService.delete(id);
        } catch (Exception e) {
            if(e.getMessage() != null) {
                if (e.getMessage().equals("Offer type with given id doesn't exist"))
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

   public  OfferTypeController(){
       offerTypeMapper = new OfferTypeMapper();
   }
}
