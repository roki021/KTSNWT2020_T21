package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, Integer> {

}