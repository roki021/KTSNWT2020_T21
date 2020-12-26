package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.UserDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.services.GuestService;
import com.culturaloffers.maps.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
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
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

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
    public void testGetAllUsers() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<UserDTO>> responseEntity =
                restTemplate.exchange("/users", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<UserDTO>>() {});

        List<UserDTO> users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_USER_ALL, users.size());
        assertEquals(DB_GUEST_USERNAME, users.get(DB_USER_ROW_NUM).getUsername());
    }

    @Test
    public void testGetFirstPageUsers() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<UserDTO>> responseEntity =
                restTemplate.exchange("/users?page=0&size=10", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<UserDTO>>() {});

        List<UserDTO> users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_USER_EXPECTED, users.size());
        assertEquals(DB_GUEST_USERNAME, users.get(DB_USER_ROW_NUM).getUsername());
    }

    @Test
    public void testGetSecondPageUsers() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<UserDTO>> responseEntity =
                restTemplate.exchange("/users?page=1&size=10", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<UserDTO>>() {});

        List<UserDTO> users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_USER_SIZE_SECOND, users.size());
    }

    @Test
    public void testGetUserGuest() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/users/" + DB_GUEST_ID, HttpMethod.GET, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_GUEST_USERNAME, user.getUsername());
    }

    @Test
    public void testGetUserAdmin() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/users/" + DB_ADMIN_ID, HttpMethod.GET, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_ADMIN_USERNAME, user.getUsername());
    }

    @Test
    public void testDeleteUser() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        User user = guestService.insert(new Guest(
            NEW_GUEST_FIRST_NAME,
            NEW_GUEST_LAST_NAME,
            NEW_GUEST_EMAIL_ADDRESS,
            NEW_GUEST_USERNAME,
            NEW_GUEST_PASSWORD
        ));
        int size = userService.getAllUsers().size();

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/users/" + user.getId(),
                        HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, userService.getAllUsers().size());
    }
}
