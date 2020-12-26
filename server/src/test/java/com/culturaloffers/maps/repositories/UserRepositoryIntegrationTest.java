package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.UserConstants.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test-user-geo.properties")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User found = userRepository.findByUsername(DB_USER_USERNAME);
        assertEquals(DB_USER_USERNAME, found.getUsername());
    }

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll(PageRequest.of(DB_USER_PAGE, DB_USER_SIZE)).getContent();
        assertEquals(DB_USER_EXPECTED, users.size());
    }

    @Test
    public void testFindByEmailAddress() {
        User user = userRepository.findByEmailAddress(DB_GUEST_EMAIL_ADDRESS);
        assertEquals(DB_GUEST_EMAIL_ADDRESS, user.getEmailAddress());
    }

    @Test
    public void testFindByUsernameAdmin() {
        User user = userRepository.findByUsername(DB_ADMIN_USERNAME);
        assertEquals(DB_ADMIN_USERNAME, user.getUsername());
    }

    @Test
    public void testFindByUsernameGuest() {
        User user = userRepository.findByUsername(DB_GUEST_USERNAME);
        assertEquals(DB_GUEST_USERNAME, user.getUsername());
    }
}
