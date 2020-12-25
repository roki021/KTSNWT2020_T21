package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.GuestControllerIntegrationTest;
import com.culturaloffers.maps.controllers.UserControllerIntegrationTest;
import com.culturaloffers.maps.repositories.AdminRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.GeoLocationRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.GuestRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.UserRepositoryIntegrationTest;
import com.culturaloffers.maps.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({UserRepositoryIntegrationTest.class, GuestRepositoryIntegrationTest.class,
        AdminRepositoryIntegrationTest.class, GeoLocationRepositoryIntegrationTest.class,
        GuestServiceUnitTest.class, GuestServiceIntegrationTest.class,
        UserServiceUnitTest.class, UserServiceIntegrationTest.class,
        GuestControllerIntegrationTest.class, UserControllerIntegrationTest.class,
        GeoLocationServiceUnitTest.class, GeoLocationServiceIntegrationTest.class
})
@TestPropertySource("classpath:test-1.properties")
public class SuitePartOne {
}
