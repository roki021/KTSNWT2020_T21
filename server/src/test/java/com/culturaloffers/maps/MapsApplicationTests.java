package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.CulturalOfferControllerTest;
import com.culturaloffers.maps.repositories.CulturalOfferRepositoryTest;
import com.culturaloffers.maps.services.CulturalOfferServiceTest;
import com.culturaloffers.maps.services.OfferNewsServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		CulturalOfferServiceTest.class,
		CulturalOfferRepositoryTest.class,
		OfferNewsServiceTest.class
})
class MapsApplicationTests {

}
