package com.culturaloffers.maps.controllers;


import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.dto.SubscriptionDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.services.GradeService;
import com.culturaloffers.maps.services.SubscriptionService;
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

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class SubscriptionControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SubscriptionService subscriptionService;

    private String accessToken;

    private HttpHeaders httpHeaders;

    private void login(String username, String password) throws NullPointerException {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Before
    public void setUp() {
        login("perica", "12345");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessToken);
    }

    @Test
    @Transactional
    public void testGetSubscriptionsByOfferId() {
        //subscriptionService.addSubscription(new SubscriptionDTO(1004, 1));
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<SubscriptionDTO>> responseEntity =
                restTemplate.exchange("/subscription/user/" + 1001, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<SubscriptionDTO>>() {});

        List<SubscriptionDTO> subs = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, subs.size());
    }

    @Test
    @Transactional
    public void okAddSubscription() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubscriptionDTO(1004, 1), httpHeaders);
        ResponseEntity<SubscriptionDTO> responseEntity =
                restTemplate.exchange("/subscription", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<SubscriptionDTO>() {});

        SubscriptionDTO sub = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, sub.getCulturalOfferId());

        subscriptionService.deleteSubscription(new SubscriptionDTO(1004, 1));
    }

    @Test
    @Transactional
    public void testDeleteGradeSubscription() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubscriptionDTO(1001, 1), httpHeaders);

        subscriptionService.addSubscription(new SubscriptionDTO(1002, 1));

        int size = subscriptionService.getAllCulturalOfferSubscribers(1).size();

        ResponseEntity<Map<String, Boolean>> responseEntity =
                restTemplate.exchange("/subscription",
                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<Map<String, Boolean>>(){});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
