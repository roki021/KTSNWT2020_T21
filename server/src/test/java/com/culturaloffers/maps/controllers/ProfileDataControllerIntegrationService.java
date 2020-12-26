package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.PasswordDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.services.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.ProfileConstatnts.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-profile.properties")
public class ProfileDataControllerIntegrationService {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProfileService profileService;

    private String accessToken;

    private HttpHeaders httpHeaders;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void login(String username, String password) throws NullPointerException {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Before
    public void setUp() {
        login(USER_NAME, USER_PASSWORD);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessToken);
    }

    @Test
    public void okTestGuestProfile(){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID, HttpMethod.GET, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(profile.getUsername(), USER_NAME);
        assertEquals(profile.getId(), USER_ID);
    }

    @Test
    public void notFoundTestGuestProfile(){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + FAKE_ID, HttpMethod.GET, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void unauthorizedTestGuestProfile(){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + DIFFERENT_USER_ID, HttpMethod.GET, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void okTestUpdateProfile() throws Exception{
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setUsername(NEW_USER);
        guestDTO.setFirstName(NEW_FIRST_NAME);
        guestDTO.setLastName(USER_LAST_NAME);
        guestDTO.setEmailAddress(NEW_EMAIL);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID, HttpMethod.PUT, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(profile.getUsername(), NEW_USER);
        assertEquals(profile.getId(), USER_ID);
        assertEquals(profile.getFirstName(), NEW_FIRST_NAME);
        assertEquals(profile.getEmailAddress(), NEW_EMAIL);

        Guest rewindGuest = new Guest();
        rewindGuest.setUsername(USER_NAME);
        rewindGuest.setFirstName(USER_FIRST_NAME);
        rewindGuest.setLastName(USER_LAST_NAME);
        rewindGuest.setEmailAddress(USER_EMAIL);
        rewindGuest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        profileService.updateProfile(USER_ID,rewindGuest, NEW_USER);
    }

    @Test
    public void notFoundTestUpdateProfile(){
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setUsername(NEW_USER);
        guestDTO.setFirstName(NEW_FIRST_NAME);
        guestDTO.setLastName(USER_LAST_NAME);
        guestDTO.setEmailAddress(NEW_EMAIL);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + FAKE_ID, HttpMethod.PUT, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void unauthorizedTestUpdateProfile(){
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setUsername(NEW_USER);
        guestDTO.setFirstName(NEW_FIRST_NAME);
        guestDTO.setLastName(USER_LAST_NAME);
        guestDTO.setEmailAddress(NEW_EMAIL);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + DIFFERENT_USER_ID, HttpMethod.PUT, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void hasEmailTestUpdateProfile(){
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setUsername(USER_NAME);
        guestDTO.setFirstName(NEW_FIRST_NAME);
        guestDTO.setLastName(USER_LAST_NAME);
        guestDTO.setEmailAddress(DIFFERENT_USER_EMAIL);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID, HttpMethod.PUT, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void hasUserNameTestUpdateProfile(){
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setUsername(DIFFERENT_USER_NAME);
        guestDTO.setFirstName(NEW_FIRST_NAME);
        guestDTO.setLastName(USER_LAST_NAME);
        guestDTO.setEmailAddress(USER_EMAIL);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID, HttpMethod.PUT, httpEntity,
                        GuestDTO.class);
        GuestDTO profile = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(profile);
    }

    @Test
    public void okTestChangePassword() throws Exception{
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(USER_PASSWORD);
        passwordDTO.setRepetedPassword(REPEATED_USER_PASSWORD);
        passwordDTO.setNewPassword(NEW_USER_PASSWORD);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(passwordDTO, httpHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID + "/change-password", HttpMethod.PUT, httpEntity,
                        Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        profileService.ChangePassword(USER_ID, NEW_USER_PASSWORD, USER_PASSWORD, USER_NAME);
    }

    @Test
    public void notFoundTestChangePassword(){
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(USER_PASSWORD);
        passwordDTO.setRepetedPassword(REPEATED_USER_PASSWORD);
        passwordDTO.setNewPassword(NEW_USER_PASSWORD);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(passwordDTO, httpHeaders);
        ResponseEntity<PasswordDTO> responseEntity =
                restTemplate.exchange("/profile/" + FAKE_ID + "/change-password", HttpMethod.PUT, httpEntity,
                        PasswordDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void unauthorizedTestChangePassword(){
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(USER_PASSWORD);
        passwordDTO.setRepetedPassword(REPEATED_USER_PASSWORD);
        passwordDTO.setNewPassword(NEW_USER_PASSWORD);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(passwordDTO, httpHeaders);
        ResponseEntity<PasswordDTO> responseEntity =
                restTemplate.exchange("/profile/" + DIFFERENT_USER_ID + "/change-password", HttpMethod.PUT, httpEntity,
                        PasswordDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void badPasswordTestChangePassword(){
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(NEW_USER_PASSWORD);
        passwordDTO.setRepetedPassword(REPEATED_USER_PASSWORD);
        passwordDTO.setNewPassword(NEW_USER_PASSWORD);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(passwordDTO, httpHeaders);
        ResponseEntity<PasswordDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID + "/change-password", HttpMethod.PUT, httpEntity,
                        PasswordDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void badRepeatPasswordTestChangePassword(){
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(USER_PASSWORD);
        passwordDTO.setRepetedPassword(USER_PASSWORD);
        passwordDTO.setNewPassword(NEW_USER_PASSWORD);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(passwordDTO, httpHeaders);
        ResponseEntity<PasswordDTO> responseEntity =
                restTemplate.exchange("/profile/" + USER_ID + "/change-password", HttpMethod.PUT, httpEntity,
                        PasswordDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
