package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.UserDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.helper.GuestMapper;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.services.GuestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;
import java.util.List;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class GuestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GuestService guestService;

    private String accessToken;

    private HttpHeaders httpHeaders;

    private void login(String username, String password) throws NullPointerException {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Before
    public void setUp() {
        login(DB_ADMIN_USERNAME, DB_ADMIN_PASSWORD);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessToken);
    }

    @Test
    public void testGetAllGuests() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<GuestDTO>> responseEntity =
                restTemplate.exchange("/guests", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<GuestDTO>>() {});

        List<GuestDTO> guests = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_GUEST_ALL, guests.size());
        assertEquals(DB_GUEST_USERNAME, guests.get(DB_GUEST_ROW_NUM).getUsername());
    }

    @Test
    public void testGetFirstPageGuests() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<GuestDTO>> responseEntity =
                restTemplate.exchange("/guests?page=0&size=10", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<GuestDTO>>() {});

        List<GuestDTO> guests = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_GUEST_EXPECTED, guests.size());
        assertEquals(DB_GUEST_USERNAME, guests.get(DB_GUEST_ROW_NUM).getUsername());
    }

    @Test
    public void testGetSecondPageGuests() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<GuestDTO>> responseEntity =
                restTemplate.exchange("/guests?page=1&size=10", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<GuestDTO>>() {});

        List<GuestDTO> users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_GUEST_SIZE_SECOND, users.size());
    }

    @Test
    public void testGetGuest() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/guests/" + DB_GUEST_ID, HttpMethod.GET, httpEntity, GuestDTO.class);

        GuestDTO guest = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(guest);
        assertEquals(DB_GUEST_USERNAME, guest.getUsername());
    }

    @Test
    public void testGetGuestNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/guests/" + DB_ADMIN_ID, HttpMethod.GET, httpEntity, GuestDTO.class);

        GuestDTO guest = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(guest);
    }

    @Test
    public void testDeleteUser() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        Guest guest = guestService.insert(new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        ));
        int size = guestService.getAllGuests().size();

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/guests/" + guest.getId(),
                        HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, guestService.getAllGuests().size());
    }

    @Test
    public void testDeleteUserNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        int size = guestService.getAllGuests().size();

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/guests/" + DB_ADMIN_ID,
                        HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(size, guestService.getAllGuests().size());
    }

    @Test
    public void testAddNewGuest() {
        GuestDTO guestDTO = new GuestDTO(
            null,
            NEW_GUEST_FIRST_NAME,
            NEW_GUEST_LAST_NAME,
            NEW_GUEST_EMAIL_ADDRESS,
            NEW_GUEST_USERNAME
        );
        guestDTO.setPassword(NEW_GUEST_PASSWORD);
        HttpEntity<GuestDTO> httpEntity = new HttpEntity<GuestDTO>(guestDTO, httpHeaders);
        int size = guestService.getAllGuests().size();

        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/guests",
                        HttpMethod.POST, httpEntity, GuestDTO.class);

        GuestDTO guest = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(guest);
        assertEquals(NEW_GUEST_USERNAME, guest.getUsername());

        List<Guest> guests = guestService.getAllGuests();
        assertEquals(size + 1, guests.size());
        assertTrue(guests.stream().anyMatch(x -> x.getUsername().equals(NEW_GUEST_USERNAME)));

        guestService.delete(guest.getId());
    }

    @Test
    public void testUpdateGuest() {
        GuestDTO guestDTO = new GuestMapper().toDto(guestService.getGuestById(DB_GUEST_ID));
        guestDTO.setPassword(guestService.getGuestById(DB_GUEST_ID).getPassword());
        guestDTO.setUsername(NEW_GUEST_USERNAME);
        HttpEntity<GuestDTO> httpEntity = new HttpEntity<GuestDTO>(guestDTO, httpHeaders);
        ResponseEntity<GuestDTO> responseEntity =
                restTemplate.exchange("/guests/" + DB_GUEST_ID, HttpMethod.PUT, httpEntity, GuestDTO.class);

        GuestDTO guest = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(guest);
        assertEquals(DB_GUEST_ID, guest.getId());
        assertEquals(NEW_GUEST_USERNAME, guest.getUsername());

        Guest dbGuest = guestService.getGuestById(DB_GUEST_ID);
        assertEquals(DB_GUEST_ID, dbGuest.getId());
        assertEquals(NEW_GUEST_USERNAME, dbGuest.getUsername());

        dbGuest.setUsername(DB_GUEST_USERNAME);
        guestService.update(dbGuest.getId(), dbGuest);
    }
}
