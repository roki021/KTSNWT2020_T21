package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.ProfileDataControllerIntegrationService;
import com.culturaloffers.maps.services.ProfileServiceIntegrationTest;
import com.culturaloffers.maps.services.ProfileServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({ProfileServiceIntegrationTest.class, ProfileServiceUnitTest.class,
        ProfileDataControllerIntegrationService.class
})
@TestPropertySource("classpath:test-profile.properties")
public class SuiteProfile {
}
