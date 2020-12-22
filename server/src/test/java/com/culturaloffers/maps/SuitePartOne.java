package com.culturaloffers.maps;

import com.culturaloffers.maps.repositories.AdminRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.GeoLocationRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.GuestRepositoryIntegrationTest;
import com.culturaloffers.maps.repositories.UserRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({UserRepositoryIntegrationTest.class, GuestRepositoryIntegrationTest.class,
        AdminRepositoryIntegrationTest.class, GeoLocationRepositoryIntegrationTest.class})
@TestPropertySource("classpath:test-1.properties")
public class SuitePartOne {
}
