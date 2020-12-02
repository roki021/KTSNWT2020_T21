package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.repositories.OfferNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferNewsService {

    @Autowired
    private OfferNewsRepository repository;

    public OfferNews create(OfferNews news) throws Exception {
        if (repository.findByTitle(news.getTitle()) != null)
            throw new Exception("Title of an offer news must be unique");
        return repository.save(news);
    }

    public OfferNews findOne(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<OfferNews> findAll(){
        return repository.findAll();
    }

    public Page<OfferNews> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public void delete(Integer id) throws Exception {
        if (repository.findById(id).orElse(null) == null)
            throw new Exception("Offer news with given id does not exist");
        repository.deleteById(id);
    }

    public OfferNews update(Integer id, OfferNews news) throws Exception {
        if (repository.findById(id).orElse(null) == null)
            throw new Exception("Offer news with given id does not exist");
        if (repository.findByTitleAndIdNot(news.getTitle(), id) != null)
            throw new Exception("Offer news with given title already exists");
        news.setId(id);
        return repository.save(news);
    }

}
