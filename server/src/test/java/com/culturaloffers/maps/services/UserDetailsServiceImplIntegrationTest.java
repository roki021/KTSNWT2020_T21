package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.model.VerificationToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.culturaloffers.maps.constants.VerificationTokenConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByToken() {
        User user = userDetailsService.getUser(TOKEN_ONE);
        assertEquals(EXPECTED_USERNAME, user.getUsername());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByBadToken() {
        User user = userDetailsService.getUser(BAD_TOKEN);
    }

    @Test
    public void testGetVerificationToken() {
        VerificationToken token = userDetailsService.getVerificationToken(TOKEN_ONE);
        assertEquals(TOKEN_ONE, token.getToken());
    }

    @Test
    public void testSaveToken() {
        VerificationToken token = userDetailsService.createVerificationToken(
                userService.getUserById(NEW_USER_ID),
                NEW_TOKEN
        );
        assertEquals(NEW_TOKEN, token.getToken());
    }
}
