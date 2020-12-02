package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.CulturalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Integer> {

    CulturalOffer findByTitle(String title);

    CulturalOffer findByTitleAndIdNot(String title, Integer integer);
}