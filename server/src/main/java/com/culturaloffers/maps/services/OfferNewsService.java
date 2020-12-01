package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.repositories.OfferNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferNewsService {

    @Autowired
    private OfferNewsRepository repository;

    public OfferNews save(OfferNews offer){
        return repository.save(offer);
    }

    public OfferNews findOne(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<OfferNews> findAll(){
        return repository.findAll();
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

}
