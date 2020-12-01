package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.services.CulturalOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/offers")
public class CulturalOfferController {

    @Autowired
    private CulturalOfferService service;

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addCulturalOffer(@RequestBody CulturalOfferDTO dto){
        CulturalOffer culturalOffer = new CulturalOffer(dto);
        service.save(culturalOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/all")
    public ResponseEntity<List<CulturalOfferDTO>> findAll(){
        List<CulturalOfferDTO> ret = new ArrayList<CulturalOfferDTO>();
        for (CulturalOffer offer: service.findAll()){
            CulturalOfferDTO dto1 = new CulturalOfferDTO(offer);
            ret.add(dto1);
            System.out.println("offer "+offer.getId());
            for (OfferNews news: offer.getOfferNews())
                System.out.println("\t"+news.getTitle()+"-"+news.getDescription());
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PutMapping(value="/update")
    public ResponseEntity<Void> updateOffer(@RequestBody CulturalOfferDTO dto){
        CulturalOffer culturalOffer = service.findOne(dto.id);
        culturalOffer.setDescription(dto.description);
        culturalOffer.setTitle(dto.title);
        culturalOffer.setImageUrls(dto.imageUrls);
        service.save(culturalOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
