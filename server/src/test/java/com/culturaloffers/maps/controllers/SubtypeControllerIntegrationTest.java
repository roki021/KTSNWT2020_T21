package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.SubtypeDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.services.SubtypeService;
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

import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-types.properties")
public class SubtypeControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SubtypeService subtypeService;

    private String accessToken;

    public void login(String username, String password) {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Test
    public void testGetAllSubtypes(){
        ResponseEntity<SubtypeDTO[]> responseEntity = restTemplate.getForEntity("/subtypes", SubtypeDTO[].class);

        SubtypeDTO[] subtypeDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TOTAL_DB_SUBTYPES, subtypeDTOS.length);
        assertEquals(DB_SUBTYPE, subtypeDTOS[0].getName());
    }

    @Test
    public void testGetAllSubtypesPageable(){
        ResponseEntity<SubtypeDTO[]> responseEntity = restTemplate.getForEntity("/subtypes/by-page?page=0&size=2", SubtypeDTO[].class);

        SubtypeDTO[] subtypeDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(PAGE_SIZE, subtypeDTOS.length);
        assertEquals(DB_SUBTYPE, subtypeDTOS[0].getName());
    }

    @Test
    public void foundTestGetSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + VALID_DB_SUBTYPE_ID, HttpMethod.GET, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(subtypeDTO);
        assertEquals(DB_SUBTYPE, subtypeDTO.getName());
    }

    @Test
    public void notFoundTestGetSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + INVALID_DB_SUBTYPE_ID, HttpMethod.GET, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(subtypeDTO);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void okTestCreateSubtype() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(null, NEW_SUBTYPE_NAME, OFFER_TYPE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes", HttpMethod.POST, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(subtypeDTO);
        assertEquals(NEW_SUBTYPE_NAME, subtypeDTO.getName());

        subtypeService.delete(subtypeDTO.getId());
    }

    @Test
    public void hasNameTestCreateSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(null, DB_SUBTYPE, OFFER_TYPE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes", HttpMethod.POST, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(subtypeDTO);
    }

    @Test
    public void typeNotFoundTestCreateSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(null, FAKE_SUBTYPE_NAME, NOT_OFFER_TYPE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes", HttpMethod.POST, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(subtypeDTO);
    }

    @Test
    public void okTestUpdateSubtype() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(VALID_DB_SUBTYPE_ID, UPDATE_SUBTYPE_NAME, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + VALID_DB_SUBTYPE_ID, HttpMethod.PUT, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(subtypeDTO);
        assertEquals(VALID_DB_SUBTYPE_ID, subtypeDTO.getId());
        assertEquals(UPDATE_SUBTYPE_NAME, subtypeDTO.getName());

        Subtype dbSubtype = subtypeService.findOne(VALID_DB_SUBTYPE_ID);
        assertEquals(VALID_DB_SUBTYPE_ID, dbSubtype.getId());
        assertEquals(UPDATE_SUBTYPE_NAME, dbSubtype.getName());

        // vracanje podatka na staru vrednost
        dbSubtype.setName(DB_SUBTYPE);
        subtypeService.update(dbSubtype, dbSubtype.getId());


    }

    @Test
    public void hasNameTestUpdateSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(VALID_DB_SUBTYPE_ID, SUBTYPE_NAME, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + VALID_DB_SUBTYPE_ID, HttpMethod.PUT, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(subtypeDTO);
    }

    @Test
    public void notFoundTestUpdateSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        SubtypeDTO dto = new SubtypeDTO(INVALID_DB_SUBTYPE_ID, FAKE_SUBTYPE_NAME, null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(dto, headers);

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + INVALID_DB_SUBTYPE_ID, HttpMethod.PUT, httpEntity, SubtypeDTO.class);

        SubtypeDTO subtypeDTO = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(subtypeDTO);
    }

    @Test
    public void okTestDeleteSubtype() throws Exception {
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(headers);

        Subtype subtype = new Subtype();
        subtype.setName(NEW_SUBTYPE_NAME);

        Subtype created = subtypeService.create(subtype, OFFER_TYPE);
        List<Subtype> subtypes = subtypeService.findAll();
        Subtype s = subtypeService.findOne(created.getId());
        int size = subtypeService.findAll().size();
        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + created.getId(), HttpMethod.DELETE, httpEntity, SubtypeDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, subtypeService.findAll().size());


    }

    @Test
    public void hasOfferTestDeleteSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(headers);
        int size = subtypeService.findAll().size();
        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + VALID_DB_SUBTYPE_ID, HttpMethod.DELETE, httpEntity, SubtypeDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, subtypeService.findAll().size());
    }

    @Test
    public void notFoundTestDeleteSubtype(){
        login(USER, PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<SubtypeDTO> httpEntity = new HttpEntity<SubtypeDTO>(headers);
        int size = subtypeService.findAll().size();

        ResponseEntity<SubtypeDTO> responseEntity = restTemplate.exchange("/subtypes/" + INVALID_DB_SUBTYPE_ID, HttpMethod.DELETE, httpEntity, SubtypeDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(size, subtypeService.findAll().size());
    }
}
