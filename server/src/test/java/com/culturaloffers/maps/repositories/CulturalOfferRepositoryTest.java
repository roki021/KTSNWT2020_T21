package com.culturaloffers.maps.repositories;

import static com.culturaloffers.maps.constants.CulturalOfferConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.culturaloffers.maps.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-offer.properties")
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

    @Test
    public void testFindAll(){
        List<CulturalOffer> all = repository.findAll();
        assertThat(all.size()).isEqualTo(15);
    }

    @Test
    public void testFindAllByZoom() {
        List<CulturalOffer> culturalOffers = repository.findAllByZoom(
                LOWER_LATITUDE,
                UPPER_LATITUDE,
                UPPER_LONGITUDE,
                LOWER_LONGITUDE
        );

        assertThat(culturalOffers.size()).isEqualTo(EXPECTED_OFFERS);
    }
}
