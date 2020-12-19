package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.CulturalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Integer> {

    CulturalOffer findByTitle(String title);

    CulturalOffer findByTitleAndIdNot(String title, Integer integer);

    @Query(
            value = "SELECT * FROM CULTURAL_OFFER C JOIN GEO_LOCATION G ON C.GEO_LOCATION_ID=G.ID AND " +
                    "G.LATITUDE BETWEEN ?1 AND ?2 AND G.LONGITUDE BETWEEN ?3 AND ?4",
            nativeQuery = true
    )
    List<CulturalOffer> findAllByZoom(
            double latitudeLowerCorner,
            double latitudeUpperCorner,
            double longitudeUpperCorner,
            double longitudeLowerCorner
    );
}