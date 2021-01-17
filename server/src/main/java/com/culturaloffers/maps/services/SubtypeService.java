package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.repositories.SubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtypeService {
    @Autowired
    private SubtypeRepository subtypeRepository;
    @Autowired
    private OfferTypeService offerTypeService;

    public List<Subtype> findAll() {

        return subtypeRepository.findAll();
    }

    public Page<Subtype> findAll(Integer id, Pageable pageable) {

        return subtypeRepository.findAllByOfferTypeId(id, pageable);
    }

    public Subtype findOne(Integer id) {

        return subtypeRepository.findById(id).orElse(null);
    }

    public Subtype findByName(String name) {

        return subtypeRepository.findByName(name);
    }

    public List<Subtype> findByOfferType(Integer offerTypeId) {

        return subtypeRepository.findByOfferTypeId(offerTypeId);
    }

    public Subtype create(Subtype entity, String offerTypeName) throws Exception {
        if(subtypeRepository.findByName(entity.getName()) != null){
            throw new Exception("Subtype with given name already exists.");
        }

        OfferType offerType = offerTypeService.findOneName(offerTypeName);
        if(offerType == null) {
            throw new Exception("Chosen offer type doesn't exist.");
        }
        entity.setOfferType(offerType);

        return subtypeRepository.save(entity);
    }

    public Subtype update(Subtype entity, Integer id) throws Exception {
        Subtype existingSubtype =  subtypeRepository.findById(id).orElse(null);
        if(existingSubtype == null){
            throw new Exception("Subtype with given id doesn't exist");
        }
        existingSubtype.setName(entity.getName());
        if(subtypeRepository.findByNameAndIdNot(existingSubtype.getName(), id) != null){
            throw new Exception("Subtype with given name already exists");
        }
        return subtypeRepository.save(existingSubtype);
    }

    public void delete(Integer id) throws Exception {
        Subtype existingSubtype = subtypeRepository.findById(id).orElse(null);
        if(existingSubtype == null){
            throw new Exception("Subtype with given name doesn't exist");
        }

        if(existingSubtype.getCulturalOffers() != null && existingSubtype.getCulturalOffers().size() != 0){
            throw new Exception("There exists a cultural offers of the given subtype");
        }
        subtypeRepository.delete(existingSubtype);
    }
}
