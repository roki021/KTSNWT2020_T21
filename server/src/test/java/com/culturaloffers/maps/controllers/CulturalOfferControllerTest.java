package com.culturaloffers.maps.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOfferControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    @Before
    public void login() {
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/auth/login",
                        new UserLoginDTO("admin", "admin"),
                        String.class);
        accessToken = responseEntity.getBody();
    }

    @Test
    public void testGetAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        ResponseEntity<CulturalOfferDTO[]> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.GET, httpEntity, CulturalOfferDTO[].class);
        CulturalOfferDTO[] dtos = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtos).isNotNull();
        assertThat(dtos).isNotEmpty();
        assertThat(dtos.length).isEqualTo(15);
    }

}
