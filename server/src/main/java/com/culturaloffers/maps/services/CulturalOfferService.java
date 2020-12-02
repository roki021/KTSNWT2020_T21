package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CulturalOfferService {

    @Autowired
    private CulturalOfferRepository repository;

    public CulturalOffer create(CulturalOffer offer) throws Exception {
        if (repository.findByTitle(offer.getTitle()) != null)
            throw new Exception("Title of a cultural offer must be unique");
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
            throw new Exception("Cultural offer with given name already exists");
        offer.setId(id);
        return repository.save(offer);
    }

}
