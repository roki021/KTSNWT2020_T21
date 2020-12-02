package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.dto.OfferNewsDTO;
import com.culturaloffers.maps.helper.OfferNewsMapper;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.OfferNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/news")
public class OfferNewsController {

    @Autowired
    private OfferNewsService service;

    @Autowired
    private CulturalOfferService culturalOfferService;

    private OfferNewsMapper mapper = new OfferNewsMapper();

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferNewsDTO> addOfferNews(@RequestBody OfferNewsDTO dto){
        OfferNews offerNews = mapper.toEntity(dto);
        try {
            offerNews.setCulturalOffer(culturalOfferService.findOne(dto.getCulturalOfferId()));
            if (offerNews.getCulturalOffer() == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            service.create(offerNews);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.toDto(offerNews), HttpStatus.OK);
    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<OfferNewsDTO>> getAll(){
        List<OfferNewsDTO> ret = mapper.toDtoList(service.findAll());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(value="/page", method = RequestMethod.GET)
    public ResponseEntity<Page<OfferNewsDTO>> getAllPageable(Pageable pageable){
        Page<OfferNews> page = service.findAll(pageable);
        List<OfferNewsDTO> dtos = mapper.toDtoList(page.toList());
        Page<OfferNewsDTO> ret = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferNewsDTO> findOfferNews(@PathVariable Integer id){
        OfferNews news = service.findOne(id);
        if (news == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapper.toDto(news), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferNewsDTO> updateNews(@RequestBody OfferNewsDTO dto, @PathVariable Integer id){
        OfferNews offerNews = mapper.toEntity(dto);
        try{
            CulturalOffer offer = service.findOne(id).getCulturalOffer();
            offerNews.setCulturalOffer(offer);
            service.update(id, offerNews);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.toDto(offerNews), HttpStatus.OK);
    }

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
