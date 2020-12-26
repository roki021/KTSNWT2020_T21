package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.CulturalOfferControllerTest;
import com.culturaloffers.maps.controllers.OfferNewsControllerTest;
import com.culturaloffers.maps.repositories.CulturalOfferRepositoryTest;
import com.culturaloffers.maps.services.CulturalOfferServiceTest;
import com.culturaloffers.maps.services.OfferNewsServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CulturalOfferRepositoryTest.class,
        CulturalOfferServiceTest.class,
        OfferNewsServiceTest.class,
        CulturalOfferControllerTest.class,
        OfferNewsControllerTest.class
})
@TestPropertySource("classpath:test-2.properties")
public class SuitePartTwo {
}
