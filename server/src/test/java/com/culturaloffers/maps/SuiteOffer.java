package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.CulturalOfferControllerTest;
import com.culturaloffers.maps.controllers.OfferNewsControllerTest;
import com.culturaloffers.maps.controllers.SearchControllerIntegrationTest;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.repositories.CulturalOfferRepositoryTest;
import com.culturaloffers.maps.repositories.OfferNewsRepositoryTest;
import com.culturaloffers.maps.repositories.SearchRepositoryIntegrationTest;
import com.culturaloffers.maps.services.CulturalOfferServiceIntegrationTest;
import com.culturaloffers.maps.services.OfferNewsServiceIntegrationTest;
import com.culturaloffers.maps.services.SearchServiceIntegrationTest;
import com.culturaloffers.maps.services.SearchServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CulturalOfferRepositoryTest.class,
        OfferNewsRepositoryTest.class,
        CulturalOfferServiceIntegrationTest.class,
        OfferNewsServiceIntegrationTest.class,
        CulturalOfferControllerTest.class,
        OfferNewsControllerTest.class,
        SearchRepositoryIntegrationTest.class,
        SearchServiceUnitTest.class,
        SearchServiceIntegrationTest.class,
        SearchControllerIntegrationTest.class
})
@TestPropertySource("classpath:test-offer.properties")
public class SuiteOffer {
}
