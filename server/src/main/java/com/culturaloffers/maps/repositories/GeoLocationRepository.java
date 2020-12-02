package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.GeoLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Integer> {

    GeoLocation findByAddress(String address);

    GeoLocation findByLatitudeAndLongitude(double latitude, double longitude);

    Page<GeoLocation> findAll(Pageable pageable);
}