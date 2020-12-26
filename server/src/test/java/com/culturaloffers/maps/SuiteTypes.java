package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.OfferTypeControllerIntegrationTest;
import com.culturaloffers.maps.controllers.SubtypeControllerIntegrationTest;
import com.culturaloffers.maps.repositories.OfferTypeRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.SubtypeRepositoryIntegrationTest;
import com.culturaloffers.maps.services.OfferTypeServiceIntegrationTest;
import com.culturaloffers.maps.services.OfferTypeServiceUnitTest;
import com.culturaloffers.maps.services.SubtypeServiceIntegrationTest;
import com.culturaloffers.maps.services.SubtypeServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({OfferTypeControllerIntegrationTest.class, SubtypeControllerIntegrationTest.class,
        OfferTypeRepositoryIntegrationTest.class, SubtypeRepositoryIntegrationTest.class,
        OfferTypeServiceUnitTest.class, OfferTypeServiceIntegrationTest.class,
        SubtypeServiceUnitTest.class, SubtypeServiceIntegrationTest.class
})
@TestPropertySource("classpath:test-types.properties")
public class SuiteTypes {
}
