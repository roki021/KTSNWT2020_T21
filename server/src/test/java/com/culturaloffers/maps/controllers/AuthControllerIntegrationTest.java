package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.helper.SmtpServerRule;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.security.TokenUtils;
import com.culturaloffers.maps.services.UserDetailsServiceImpl;
import com.culturaloffers.maps.services.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static com.culturaloffers.maps.constants.VerificationTokenConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class AuthControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Test
    public void testLoginOk() {
        UserLoginDTO userLoginDTO = new UserLoginDTO(DB_ADMIN_USERNAME, DB_ADMIN_PASSWORD);
        ResponseEntity<UserTokenStateDTO> responseEntity =
                restTemplate.exchange("/auth/login",
                        HttpMethod.POST, new HttpEntity<UserLoginDTO>(userLoginDTO), UserTokenStateDTO.class);

        UserTokenStateDTO token = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(token);
        assertEquals(DB_ADMIN_USERNAME, tokenUtils.getUsernameFromToken(token.getAccessToken()));
    }

    @Test
    public void testLoginWrongCredentials() {
        UserLoginDTO userLoginDTO = new UserLoginDTO(DB_GUEST_USERNAME, DB_ADMIN_PASSWORD);
        ResponseEntity<UserTokenStateDTO> responseEntity =
                restTemplate.exchange("/auth/login",
                        HttpMethod.POST, new HttpEntity<UserLoginDTO>(userLoginDTO), UserTokenStateDTO.class);

        UserTokenStateDTO token = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(token);
    }

    @Test
    public void testRegisterOk() throws MessagingException {
        GuestDTO guestDTO = new GuestDTO(
                null,
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME
        );
        guestDTO.setPassword(NEW_GUEST_PASSWORD);
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/register",
                        HttpMethod.POST,
                        new HttpEntity<GuestDTO>(guestDTO),
                        new ParameterizedTypeReference<>() {}
                        );

        User guest = userService.getUserByUsername(NEW_GUEST_USERNAME);
        MimeMessage[] receivedMessages = smtpServerRule.getMessages();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1, receivedMessages.length);
        assertNotNull(guest);
        assertEquals(NEW_GUEST_USERNAME, guest.getUsername());
        assertEquals(NEW_GUEST_EMAIL_ADDRESS, guest.getEmailAddress());
        assertNull(guest.getActive());

        MimeMessage current = receivedMessages[0];
        assertEquals(NEW_GUEST_EMAIL_ADDRESS, current.getAllRecipients()[0].toString());

        userService.delete(guest.getId());
    }

    @Test
    public void testRegisterExistUsername() {
        GuestDTO guestDTO = new GuestDTO(
                null,
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                DB_ADMIN_USERNAME
        );
        guestDTO.setPassword(NEW_GUEST_PASSWORD);
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/register",
                        HttpMethod.POST,
                        new HttpEntity<GuestDTO>(guestDTO),
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testRegisterExistEmailAddress() {
        GuestDTO guestDTO = new GuestDTO(
                null,
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                DB_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME
        );
        guestDTO.setPassword(NEW_GUEST_PASSWORD);
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/register",
                        HttpMethod.POST,
                        new HttpEntity<GuestDTO>(guestDTO),
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testConfirmRegistrationOk() {
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/registrationConfirm?token=" + TOKEN_ONE,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDetails user = userDetailsService.getUser(TOKEN_ONE);
        assertNotNull(user);
        assertTrue(user.isEnabled());
    }

    @Test(expected = NullPointerException.class)
    public void testConfirmRegistrationNotFound() {
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/registrationConfirm?token=" + BAD_TOKEN,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        UserDetails user = userDetailsService.getUser(BAD_TOKEN);
    }

    @Test
    public void testConfirmRegistrationExpiredDate() {
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(
                        "/auth/registrationConfirm?token=" + TOKEN_TWO,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        UserDetails user = userDetailsService.getUser(TOKEN_TWO);
        assertNotNull(user);
        assertFalse(user.isEnabled());
    }
}
