package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferNewsDTO;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.OfferNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addOfferNews(@RequestBody OfferNewsDTO dto){
        OfferNews offerNews = new OfferNews(dto);
        offerNews.setCulturalOffer(culturalOfferService.findOne(dto.culturalOfferId));
        service.save(offerNews);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/all")
    public ResponseEntity<List<OfferNewsDTO>> allOfferNews(){
        List<OfferNewsDTO> ret = new ArrayList<OfferNewsDTO>();
        for (OfferNews offer: service.findAll()){
            OfferNewsDTO dto = new OfferNewsDTO(offer);
            ret.add(dto);
            if (offer.getCulturalOffer() != null)
                System.out.println("News "+offer.getId()+"-offer: "+offer.getCulturalOffer().getTitle());
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Void> updateNews(@RequestBody OfferNewsDTO dto){
        OfferNews offerNews = service.findOne(dto.id);
        offerNews.setDescription(dto.description);
        offerNews.setTitle(dto.title);
        offerNews.setImageUrls(dto.imageUrls);
        offerNews.setDate(new Date());
        service.save(offerNews);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
