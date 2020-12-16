package com.culturaloffers.maps.services;

import com.culturaloffers.maps.helper.ImageHandler;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CulturalOfferService {

    @Autowired
    private CulturalOfferRepository repository;

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private SubtypeService subtypeService;

    public CulturalOffer create(CulturalOffer offer, String address, String subtypeName) throws Exception {
        if (repository.findByTitle(offer.getTitle()) != null)
            throw new Exception("Title of a cultural offer must be unique");
        if (geoLocationService.getByAddress(address) == null)
            throw new Exception("GeoLocation is not valid!");
        offer.setGeoLocation(geoLocationService.getByAddress(address));
        if (subtypeService.findByName(subtypeName) == null)
            throw new Exception("Subtype is not valid!");
        offer.setSubtype(subtypeService.findByName(subtypeName));
        List<String> imagePaths = new ArrayList<String>();
        for(String s : offer.getImageUrls())
        {
            imagePaths.add(ImageHandler.saveImage("src\\main\\images\\offerImages\\", s));
        }
        offer.setImageUrls(imagePaths);
        return repository.save(offer);
    }

    public CulturalOffer findOne(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<CulturalOffer> findAll(){
        return repository.findAll();
    }

    public Page<CulturalOffer> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public void delete(Integer id) throws Exception {
        if (repository.findById(id).orElse(null) == null)
            throw new Exception("Cultural offer with given id does not exist");
        repository.deleteById(id);
    }

    public CulturalOffer update(Integer id, CulturalOffer offer) throws Exception {
        if (repository.findById(id).orElse(null) == null)
            throw new Exception("Cultural offer with given id does not exist");
        if (repository.findByTitleAndIdNot(offer.getTitle(), id) != null)
            throw new Exception("Cultural offer with given title already exists");
        offer.setId(id);
        List<String> imagePaths = new ArrayList<String>();
        for(String s : offer.getImageUrls())
        {
            imagePaths.add(ImageHandler.saveImage("src\\main\\images\\offerImages\\", s));
        }
        offer.setImageUrls(imagePaths);
        return repository.save(offer);
    }

    public List<CulturalOffer> search(String value, String field) throws Exception {
        List<CulturalOffer> offers = null;

        switch (field.toLowerCase()){
            case "title":
                offers = repository.findByTitleContaining(value);
                break;
            case "description":
                offers = repository.findByDescriptionContaining(value);
                break;
            case "subtype":
                offers = repository.findBySubtypeContaining(value);
                break;
            case "type":
                offers = repository.findByTypeContaining(value);
                break;
            case "subscribers":
                if(value.equals("0")){
                    return repository.findAll();
                }
                offers = repository.findBySubscriberAmountGreaterEq(Long.parseLong(value));
                break;
            case "grade":
                if(value.equals("0")){
                    return repository.findAll();
                }
                else if(Double.parseDouble(value) < 0){
                    throw new Exception("Positive value needed");
                }
                List<CulturalOffer> pom = repository.findGraded();
                offers = new ArrayList<CulturalOffer>();
                for(CulturalOffer offer : pom){
                    double grade = 0;
                    for(Grade userGrade : offer.getUserGrades()){
                        grade += userGrade.getValue();
                    }
                    if(grade/offer.getUserGrades().size() >= Double.parseDouble(value)){
                        offers.add(offer);
                    }
                }
                break;
            default: throw new Exception("Bad search field");
        }
        return offers;
    }

}
