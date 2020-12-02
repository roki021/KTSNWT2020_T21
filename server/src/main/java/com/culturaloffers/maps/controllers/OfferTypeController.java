package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferTypeDTO;
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

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/offer-types")
public class OfferTypeController {
    @Autowired
    private OfferTypeService offerTypeService;

    @Autowired
    private SubtypeService subtypeService;

    //private CulturalContentCategoryMapper culturalContentCategoryMapper;

    //private CategoryTypeMapper categoryTypeMapper;

    //@Autowired
    //private CategoryTypeService categoryTypeService;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OfferTypeDTO>> getAllOfferTypes() {
        List<OfferType> offerTypes = offerTypeService.findAll();
        List<String> subtypes = new ArrayList<String>();
        List<OfferTypeDTO> offerTypeDTOS = new ArrayList<OfferTypeDTO>();
        for(OfferType offerType : offerTypes){
            for(Subtype subtype : offerType.getSubtypes()){
                subtypes.add(subtype.getName());
            }
            OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);
            offerTypeDTOS.add(dto);
            subtypes = new ArrayList<String>();

        }
        return new ResponseEntity<>(offerTypeDTOS, HttpStatus.OK);
    }

    @RequestMapping(value= "/by-page", method = RequestMethod.GET)
    public ResponseEntity<Page<OfferTypeDTO>> getAllOfferTypes(Pageable pageable) {
        Page<OfferType> offerTypesPage = offerTypeService.findAll(pageable);
        List<String> subtypes = new ArrayList<String>();
        List<OfferTypeDTO> offerTypeDTOS = new ArrayList<OfferTypeDTO>();
        for(OfferType offerType : offerTypesPage){
            for(Subtype subtype : offerType.getSubtypes()){
                subtypes.add(subtype.getName());
            }
            OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);
            offerTypeDTOS.add(dto);
            subtypes = new ArrayList<String>();

        }

        return new ResponseEntity<>(new PageImpl<>(offerTypeDTOS,offerTypesPage.getPageable(),offerTypesPage.getTotalElements()), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{name}", method=RequestMethod.GET)
    public ResponseEntity<OfferTypeDTO> getOfferType(@PathVariable String name){
        OfferType offerType = offerTypeService.findOne(name);
        if(offerType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<String> subtypes = new ArrayList<String>();
        for(Subtype subtype : offerType.getSubtypes()){
            subtypes.add(subtype.getName());
        }
        OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferTypeDTO> createOfferType(@RequestBody OfferTypeDTO offerTypeDTO){
        OfferType offerType;
        try {
            offerType = new OfferType();
            offerType.setName(offerTypeDTO.getName());

            //offerType = offerTypeService.create(offerType);
            Set<Subtype> subtypes = new HashSet<Subtype>();
            for(String type : offerTypeDTO.getSubtypes()){
                Subtype subtype = new Subtype();
                subtype.setName(type);
                subtype.setOfferType(offerType);
                //subtypeService.create(subtype, offerType.getName());
                subtypes.add(subtype);
            }
            offerType.setSubtypes(subtypes);
            offerType = offerTypeService.create(offerType);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<String> subtypes = new ArrayList<String>();
        for(Subtype subtype : offerType.getSubtypes()){
            subtypes.add(subtype.getName());
        }
        OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferTypeDTO> updateOfferType(
            @RequestBody OfferTypeDTO offerTypeDTO, @PathVariable Integer id){
        OfferType offerType;
        try {
            offerType = new OfferType();
            offerType.setName(offerTypeDTO.getName());
            offerType = offerTypeService.update(offerType, id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<String> subtypes = new ArrayList<String>();
        for(Subtype subtype : offerType.getSubtypes()){
            subtypes.add(subtype.getName());
        }
        OfferTypeDTO dto = new OfferTypeDTO(offerType.getId(), offerType.getName(), subtypes);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{name}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOfferType(@PathVariable String name){
        try {
            offerTypeService.delete(name);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

   /* public CulturalContentCategoryController() {

        culturalContentCategoryMapper = new CulturalContentCategoryMapper();
        categoryTypeMapper = new CategoryTypeMapper();
    }

    private List<CulturalContentCategoryDTO> toCulturalContentCategoryDTOList(List<CulturalContentCategory> culturalContentCategories){
        List<CulturalContentCategoryDTO> culturalContentCategoryDTOS = new ArrayList<>();
        for (CulturalContentCategory culturalContentCategory: culturalContentCategories) {
            culturalContentCategoryDTOS.add(culturalContentCategoryMapper.toDto(culturalContentCategory));
        }
        return culturalContentCategoryDTOS;
    }

    private List<CategoryTypeDTO> toCategoryTypeDTOList(List<CategoryType> categoryTypes) {
        List<CategoryTypeDTO> categoryTypeDTOS = new ArrayList<>();
        for (CategoryType categoryType: categoryTypes) {
            categoryTypeDTOS.add(categoryTypeMapper.toDto(categoryType));
        }
        return categoryTypeDTOS;
    }*/
}
