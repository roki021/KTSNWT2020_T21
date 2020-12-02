package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtypeRepository extends JpaRepository<Subtype, Integer> {

    Subtype findByName(String name);

    Subtype findByNameAndIdNot(String name, Integer id);

    List<Subtype> findByOfferTypeId(Integer offerTypeId);

}