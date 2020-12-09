package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.dto.SubtypeDTO;
import com.culturaloffers.maps.helper.SubtypeMapper;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.services.OfferTypeService;
import com.culturaloffers.maps.services.SubtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/subtypes")
public class SubtypeController {

    @Autowired
    private SubtypeService subtypeService;

    private SubtypeMapper subtypeMapper;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubtypeDTO>> getAllSubtypes() {
        List<Subtype> subtypes = subtypeService.findAll();

        return new ResponseEntity<>(subtypeMapper.toDtoList(subtypes), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value= "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<SubtypeDTO>> getAllSubtypes(Pageable pageable) {
        Page<Subtype> subtypesPage = subtypeService.findAll(pageable);

        return new ResponseEntity<>(new PageImpl<>(subtypeMapper.toDtoList(subtypesPage.toList()),
                subtypesPage.getPageable(),subtypesPage.getTotalElements()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<SubtypeDTO> getSubype(@PathVariable Integer id){
        Subtype subtype = subtypeService.findOne(id);
        if(subtype == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(subtypeMapper.toDto(subtype), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubtypeDTO> createSubtype(@RequestBody SubtypeDTO subtypeDTO){
        Subtype subtype;
        try {
            if(subtypeDTO.getOfferTypeName() == null){
                throw new Exception();
            }
            subtype = subtypeService.create(subtypeMapper.toEntity(subtypeDTO), subtypeDTO.getOfferTypeName());

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(subtypeMapper.toDto(subtype), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubtypeDTO> updateSubtype(
            @RequestBody SubtypeDTO subtypeDTO, @PathVariable Integer id){
        Subtype subtype;
        try {
            subtype = subtypeService.update(subtypeMapper.toEntity(subtypeDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(subtypeMapper.toDto(subtype), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSubtype(@PathVariable Integer id){
        try {
            subtypeService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public SubtypeController(){
        subtypeMapper = new SubtypeMapper();
    }
}
