package com.culturaloffers.maps.services;

import com.culturaloffers.maps.helper.ImageHandler;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.repositories.OfferNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferNewsService {

    @Autowired
    private OfferNewsRepository repository;

    @Autowired
    private CulturalOfferService offerService;

    public OfferNews create(OfferNews news, Integer id) throws Exception {
        CulturalOffer offer = offerService.findOne(id);
        if (offer == null)
            throw new Exception("Cultural offer must be assigned");
        news.setCulturalOffer(offer);
        List<String> imagePaths = new ArrayList<>();
        for(String s : news.getImageUrls())
        {
            imagePaths.add(ImageHandler.saveImage("src\\main\\images\\newsImages\\", s));
        }
        news.setImageUrls(imagePaths);
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
        news.setId(id);
        List<String> imagePaths = new ArrayList<>();
        for(String s : news.getImageUrls())
        {
            imagePaths.add(ImageHandler.saveImage("src\\main\\images\\newsImages\\", s));
        }
        news.setImageUrls(imagePaths);
        return repository.save(news);
    }

}
