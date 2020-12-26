package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferNewsDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.services.OfferNewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static com.culturaloffers.maps.constants.OfferNewsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-offer.properties")
public class OfferNewsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OfferNewsService service;

    private String accessToken;

    private HttpHeaders httpHeaders;

    @Before
    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO("admin","admin"), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessToken);
    }

    @Test
    public void testGetAll(){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<OfferNewsDTO[]> responseEntity =
                restTemplate.exchange("/news", HttpMethod.GET, httpEntity, OfferNewsDTO[].class);
        OfferNewsDTO[] dtos = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtos).isNotNull();
        assertThat(dtos).isNotEmpty();
        assertThat(dtos.length).isEqualTo(5);
    }

    @Test
    public void testFindOneNews(){
        HttpEntity httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news/"+ON_ID, HttpMethod.GET, httpEntity, OfferNewsDTO.class);
        OfferNewsDTO news = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(news).isNotNull();
        assertThat(news.getTitle()).isEqualTo(ON_TITLE);
        assertThat(news.getDescription()).isEqualTo(ON_DESCRIPTION);
        assertThat(news.getCulturalOfferId()).isEqualTo(ON_OFFER);
    }

    @Test
    public void testFindNoNews(){
        HttpEntity httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news/"+45, HttpMethod.GET, httpEntity, OfferNewsDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddOneNews(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs, ON_OFFER, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news", HttpMethod.POST, httpEntity, OfferNewsDTO.class);
        OfferNewsDTO news = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(news).isNotNull();
        assertThat(news.getId()).isNotNull();
        assertThat(news.getTitle()).isEqualTo(ON_NEW_TITLE);
        assertThat(news.getDescription()).isEqualTo(ON_NEW_DESCRIPTION);
        assertThat(news.getCulturalOfferId()).isEqualTo(ON_OFFER);
    }

    @Test
    public void testAddOneNewsWithEmptyTitle(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, "", ON_NEW_DESCRIPTION, imgs, ON_OFFER, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news", HttpMethod.POST, httpEntity, OfferNewsDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneNewsWithEmptyDescription(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, ON_NEW_TITLE, "", imgs, ON_OFFER, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news", HttpMethod.POST, httpEntity, OfferNewsDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneNewsWithInvalidOfferId(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs, 50, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news", HttpMethod.POST, httpEntity, OfferNewsDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateNews(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs, ON_OFFER, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news/"+ON_UP_ID, HttpMethod.PUT, httpEntity, OfferNewsDTO.class);
        OfferNewsDTO news = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(news).isNotNull();
        assertThat(news.getId()).isEqualTo(ON_UP_ID);
        assertThat(news.getTitle()).isEqualTo(ON_NEW_TITLE);
        assertThat(news.getDescription()).isEqualTo(ON_NEW_DESCRIPTION);
        assertThat(news.getCulturalOfferId()).isNotEqualTo(ON_OFFER);
    }

    @Test
    public void testUpdateNewsWithInvalidId(){
        ArrayList<String> imgs = new ArrayList<>();
        Date date = new Date();
        OfferNewsDTO dto = new OfferNewsDTO(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs, ON_OFFER, date);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<OfferNewsDTO> responseEntity =
                restTemplate.exchange("/news/"+54, HttpMethod.PUT, httpEntity, OfferNewsDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteNews(){
        int sizeBefore = service.findAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/news/"+ON_DEL_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(service.findOne(ON_DEL_ID)).isNull();
        assertThat(service.findAll().size()).isLessThan(sizeBefore);
    }

    @Test
    public void testDeleteNewsWithInvalidId(){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/news/"+58, HttpMethod.DELETE, httpEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
