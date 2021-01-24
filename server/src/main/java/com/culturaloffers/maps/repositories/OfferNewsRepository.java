package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.OfferNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferNewsRepository extends JpaRepository<OfferNews, Integer> {

    OfferNews findByTitle(String title);

    List<OfferNews> findByCulturalOfferId(int id);

    Page<OfferNews> findByCulturalOfferId(int id, Pageable pageable);

}