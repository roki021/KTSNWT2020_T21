package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.services.OfferTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-types.properties")
public class OfferTypeControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OfferTypeService offerTypeService;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Test
    public void testGetAllOfferTypes(){
        ResponseEntity<OfferTypeDTO[]> responseEntity = restTemplate.getForEntity("/offer-types", OfferTypeDTO[].class);

        OfferTypeDTO[] offerTypeDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TOTAL_ELEMENTS, offerTypeDTOS.length);
        assertEquals(DB_OFFER_TYPE, offerTypeDTOS[0].getName());
    }

    @Test
    public void testGetAllOfferTypesPageable(){
        ResponseEntity<OfferTypeDTO[]> responseEntity = restTemplate.getForEntity("/offer-types/by-page?page=0&size=2", OfferTypeDTO[].class);

        OfferTypeDTO[] offerTypeDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SIZE_OF_PAGE, offerTypeDTOS.length);
        assertEquals(DB_OFFER_TYPE, offerTypeDTOS[0].getName());
    }

    @Test
    public void foundTestGetOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + VALID_DB_OFFER_TYPE_ID, HttpMethod.GET, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(offerTypeDTO);
        assertEquals(DB_OFFER_TYPE, offerTypeDTO.getName());
    }

    @Test
    public void notFoundTestGetOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + INVALID_DB_OFFER_TYPE_ID, HttpMethod.GET, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(offerTypeDTO);
    }

    @Test
    //@Transactional
    //@Rollback(false)
    public void okTestCreateOfferType() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        OfferTypeDTO dto = new OfferTypeDTO(null, NEW_OFFER_TYPE, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(dto, headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types", HttpMethod.POST, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(offerTypeDTO);
        assertEquals(NEW_OFFER_TYPE, offerTypeDTO.getName());

        offerTypeService.delete(offerTypeDTO.getId());
    }

    @Test
    public void hasNameTestCreateOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        OfferTypeDTO dto = new OfferTypeDTO(null, DB_OFFER_TYPE, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(dto, headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types", HttpMethod.POST, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(offerTypeDTO);
    }


    @Test
    public void okTestUpdateOfferType() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        OfferTypeDTO dto = new OfferTypeDTO(VALID_DB_OFFER_TYPE_ID, UPDATE_OFFER_TYPE_NAME, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(dto, headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + VALID_DB_OFFER_TYPE_ID, HttpMethod.PUT, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(offerTypeDTO);
        assertEquals(VALID_DB_OFFER_TYPE_ID, offerTypeDTO.getId());
        assertEquals(UPDATE_OFFER_TYPE_NAME, offerTypeDTO.getName());

        OfferType dbOfferType = offerTypeService.findOne(VALID_DB_OFFER_TYPE_ID);
        assertEquals(VALID_DB_OFFER_TYPE_ID, dbOfferType.getId());
        assertEquals(UPDATE_OFFER_TYPE_NAME, dbOfferType.getName());

        // vracanje podatka na staru vrednost
        dbOfferType.setName(DB_OFFER_TYPE);
        offerTypeService.update(dbOfferType, dbOfferType.getId());


    }

    @Test
    public void hasNameTestUpdateOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        OfferTypeDTO dto = new OfferTypeDTO(OFFER_ID, DB_OFFER_TYPE, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(dto, headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + OFFER_ID, HttpMethod.PUT, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(offerTypeDTO);
    }

    @Test
    public void notFoundTestUpdateOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        OfferTypeDTO dto = new OfferTypeDTO(INVALID_DB_OFFER_TYPE_ID, FAKE_NAME, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(dto, headers);

        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + INVALID_DB_OFFER_TYPE_ID, HttpMethod.PUT, httpEntity, OfferTypeDTO.class);

        OfferTypeDTO offerTypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(offerTypeDTO);
    }

    @Test
    public void okTestDeleteOfferType() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(headers);

        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE);

        OfferType created = offerTypeService.create(offerType);
        int size = offerTypeService.findAll().size();
        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + created.getId(), HttpMethod.DELETE, httpEntity, OfferTypeDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, offerTypeService.findAll().size());


    }

    @Test
    public void hasOfferTestDeleteOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(headers);
        int size = offerTypeService.findAll().size();
        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + VALID_DB_OFFER_TYPE_ID, HttpMethod.DELETE, httpEntity, OfferTypeDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, offerTypeService.findAll().size());
    }

    @Test
    public void notFoundTestDeleteOfferType(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<OfferTypeDTO> httpEntity = new HttpEntity<OfferTypeDTO>(headers);
        int size = offerTypeService.findAll().size();
        ResponseEntity<OfferTypeDTO> responseEntity = restTemplate.exchange("/offer-types/" + INVALID_DB_OFFER_TYPE_ID, HttpMethod.DELETE, httpEntity, OfferTypeDTO.class);

        //OfferTypeDTO offerTypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        //assertNull(offerTypeDTO);
        assertEquals(size, offerTypeService.findAll().size());
    }
}
