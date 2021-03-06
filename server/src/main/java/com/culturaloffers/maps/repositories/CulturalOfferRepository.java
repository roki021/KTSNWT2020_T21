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

    List<CulturalOffer> findByTitleContaining(String title);

    List<CulturalOffer> findByDescriptionContaining(String description);

    @Query("Select offer from CulturalOffer offer join offer.subtype subtype where subtype.name like  %:name%")
    List<CulturalOffer> findBySubtypeContaining(String name);

    @Query("Select distinct(offer) from CulturalOffer offer join offer.subscribers subscribers where size(subscribers) >= :amount")
    List<CulturalOffer> findBySubscriberAmountGreaterEq(Integer amount);

    @Query("Select distinct(offer) from CulturalOffer offer join offer.userGrades grades where size(grades) > 0")
    List<CulturalOffer> findGraded();

    @Query("Select offer from CulturalOffer offer join offer.subtype subtype where subtype.offerType.name like  %:name%")
    List<CulturalOffer> findByTypeContaining(String name);

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