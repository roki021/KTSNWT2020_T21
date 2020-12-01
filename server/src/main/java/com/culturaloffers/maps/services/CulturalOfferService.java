package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CulturalOfferService {

    @Autowired
    private CulturalOfferRepository repository;

    public CulturalOffer save(CulturalOffer offer){
        return repository.save(offer);
    }

    public CulturalOffer findOne(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<CulturalOffer> findAll(){
        return repository.findAll();
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

}
