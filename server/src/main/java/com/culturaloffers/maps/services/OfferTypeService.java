package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.repositories.OfferTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferTypeService {
    @Autowired
    private OfferTypeRepository offerTypeRepository;
    @Autowired
    private SubtypeService subtypeService;

    public List<OfferType> findAll() {

        return offerTypeRepository.findAll();
    }

    public Page<OfferType> findAll(Pageable pageable) {

        return offerTypeRepository.findAll(pageable);
    }

    public OfferType findOne(Integer id) {

        return offerTypeRepository.findById(id).orElse(null);
    }

    public OfferType findOneName(String name) {

        return offerTypeRepository.findByName(name);
    }

    public OfferType create(OfferType entity) throws Exception {
        if(offerTypeRepository.findByName(entity.getName()) != null){
            throw new Exception("Offer type with given name already exists.");
        }

        return offerTypeRepository.save(entity);
    }

    public OfferType update(OfferType entity, Integer id) throws Exception {
        OfferType existingOfferType =  offerTypeRepository.findById(id).orElse(null);
        if(existingOfferType == null){
            throw new Exception("Offer type with given id doesn't exist");
        }
        existingOfferType.setName(entity.getName());
        if(offerTypeRepository.findByNameAndIdNot(existingOfferType.getName(), id) != null){
            throw new Exception("Offer type with given name already exists");
        }
        return offerTypeRepository.save(existingOfferType);
    }

    public void delete(Integer id) throws Exception {
        OfferType existingOfferType = offerTypeRepository.findById(id).orElse(null);
        if(existingOfferType == null){
            throw new Exception("Offer type with given id doesn't exist");
        }

        for(Subtype subtype : subtypeService.findByOfferType(existingOfferType.getId())){
            if(subtype.getCulturalOffers() != null && subtype.getCulturalOffers().size() != 0){
                throw new Exception("There exists a cultural offer of the given offer type");
            }
        }
        offerTypeRepository.delete(existingOfferType);
    }
}
