package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.OfferNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferNewsRepository extends JpaRepository<OfferNews, Integer> {

}