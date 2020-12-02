package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.dto.SubtypeDTO;
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

    //private CulturalContentCategoryMapper culturalContentCategoryMapper;

    //private CategoryTypeMapper categoryTypeMapper;

    //@Autowired
    //private CategoryTypeService categoryTypeService;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubtypeDTO>> getAllSubtypes() {
        List<Subtype> subtypes = subtypeService.findAll();
        List<SubtypeDTO> subtypeDTOS = new ArrayList<SubtypeDTO>();
        for(Subtype subtype : subtypes){
            SubtypeDTO subtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());
            subtypeDTOS.add(subtypeDTO);
        }
        return new ResponseEntity<>(subtypeDTOS, HttpStatus.OK);
    }

    @RequestMapping(value= "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<SubtypeDTO>> getAllSubtypes(Pageable pageable) {
        Page<Subtype> subtypesPage = subtypeService.findAll(pageable);
        List<SubtypeDTO> subtypeDTOS = new ArrayList<SubtypeDTO>();
        for(Subtype subtype : subtypesPage){
            SubtypeDTO subtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());
            subtypeDTOS.add(subtypeDTO);
        }
        return new ResponseEntity<>(new PageImpl<>(subtypeDTOS,subtypesPage.getPageable(),subtypesPage.getTotalElements()), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{name}", method=RequestMethod.GET)
    public ResponseEntity<SubtypeDTO> getSubype(@PathVariable String name){
        Subtype subtype = subtypeService.findOne(name);
        if(subtype == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SubtypeDTO subtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());
        return new ResponseEntity<>(subtypeDTO, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubtypeDTO> createSubtype(@RequestBody SubtypeDTO subtypeDTO){
        Subtype subtype;
        try {
            subtype = new Subtype();
            subtype.setName(subtypeDTO.getName());

            subtype = subtypeService.create(subtype, subtypeDTO.getOfferTypeName());

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SubtypeDTO newSubtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());

        return new ResponseEntity<>(newSubtypeDTO, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubtypeDTO> updateSubtype(
            @RequestBody SubtypeDTO subtypeDTO, @PathVariable Integer id){
        Subtype subtype;
        try {
            subtype = new Subtype();
            subtype.setName(subtypeDTO.getName());
            subtype = subtypeService.update(subtype, id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SubtypeDTO newSubtypeDTO = new SubtypeDTO(subtype.getId(), subtype.getName(), subtype.getOfferType().getName());
        return new ResponseEntity<>(newSubtypeDTO, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{name}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSubtype(@PathVariable String name){
        try {
            subtypeService.delete(name);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
