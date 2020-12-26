package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.CulturalOfferControllerTest;
import com.culturaloffers.maps.controllers.OfferNewsControllerTest;
import com.culturaloffers.maps.model.OfferNews;
import com.culturaloffers.maps.repositories.CulturalOfferRepositoryTest;
import com.culturaloffers.maps.repositories.OfferNewsRepositoryTest;
import com.culturaloffers.maps.services.CulturalOfferServiceIntegrationTest;
import com.culturaloffers.maps.services.OfferNewsServiceIntegrationTest;
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
        OfferNewsControllerTest.class
})
@TestPropertySource("classpath:test-2.properties")
public class SuitePartTwo {
}
