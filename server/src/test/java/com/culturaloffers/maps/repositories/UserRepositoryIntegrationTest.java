package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.culturaloffers.maps.constants.UserConstants.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User found = userRepository.findByUsername(DB_USER_USERNAME);
        System.out.println(found.getUsername());
        assertEquals(DB_USER_USERNAME, found.getUsername());
    }
}
