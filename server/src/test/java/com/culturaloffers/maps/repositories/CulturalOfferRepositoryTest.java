package com.culturaloffers.maps.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_ID;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_ADDRESS;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_DESCRIPTION;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_SUBTYPE;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_TITLE;

import com.culturaloffers.maps.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOfferRepositoryTest {

    @Autowired
    private CulturalOfferRepository repository;

    @Test
    public void testFindByTitle(){
        CulturalOffer offer = repository.findByTitle(CO_TITLE);
        assertThat(offer).isNotNull();
        assertThat(offer.getId()).isEqualTo(CO_ID);
        assertThat(offer.getSubtype().getName()).isEqualTo(CO_SUBTYPE);
        assertThat(offer.getGeoLocation().getAddress()).isEqualTo(CO_ADDRESS);
        assertThat(offer.getDescription()).isEqualTo(CO_DESCRIPTION);
    }

    @Test
    public void testFindByTitleAndIdNot(){
        CulturalOffer offer = repository.findByTitleAndIdNot(CO_TITLE, 12);
        assertThat(offer).isNotNull();
        assertThat(offer.getId()).isEqualTo(CO_ID);
        assertThat(offer.getSubtype().getName()).isEqualTo(CO_SUBTYPE);
        assertThat(offer.getGeoLocation().getAddress()).isEqualTo(CO_ADDRESS);
        assertThat(offer.getDescription()).isEqualTo(CO_DESCRIPTION);
        offer = repository.findByTitleAndIdNot(CO_TITLE, CO_ID);
        assertThat(offer).isNull();
    }
}
