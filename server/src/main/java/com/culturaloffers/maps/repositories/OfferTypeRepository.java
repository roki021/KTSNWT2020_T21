package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferTypeRepository extends JpaRepository<OfferType, Integer> {

}