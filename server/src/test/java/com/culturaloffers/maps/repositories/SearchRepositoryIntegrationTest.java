package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.SearchConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-search.properties")
public class SearchRepositoryIntegrationTest {

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;
    @Test
    public void okTestFindByTitleContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByTitleContaining(TITLE);

        assertEquals(1, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindByTitleContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByTitleContaining(BAD_TITLE);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestFindByDescriptionContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByDescriptionContaining(DESCRIPTION);

        assertEquals(1, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindByDescriptionContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByDescriptionContaining(BAD_DESCRIPTION);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestFindBySubtypeContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findBySubtypeContaining(SUBTYPE);

        assertEquals(1, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindBySubtypeContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findBySubtypeContaining(BAD_SUBTYPE);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestFindBySubscriberAmountGreaterEq(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findBySubscriberAmountGreaterEq(Integer.parseInt(SUBSCRIBERS));

        assertEquals(1, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindBySubscriberAmountGreaterEq(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findBySubscriberAmountGreaterEq(Integer.parseInt(BAD_SUBSCRIBERS));

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestFindGraded(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findGraded();

        assertEquals(4, culturalOffers.size());
    }

    @Test
    public void okTestFindByTypeContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByTypeContaining(TYPE);

        assertEquals(2, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindByTypeContaining(){
        List<CulturalOffer> culturalOffers = culturalOfferRepository.findByTypeContaining(BAD_TYPE);

        assertEquals(0, culturalOffers.size());
    }
}
