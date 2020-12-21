package com.culturaloffers.maps;

import com.culturaloffers.maps.repositories.UserRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({UserRepositoryIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {
}
