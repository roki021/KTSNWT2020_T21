package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.helper.GuestMapper;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.GradeService;
import com.culturaloffers.maps.services.GuestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class GradesControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CulturalOfferService culturalOfferService;

    @Autowired
    private GuestService guestService;

    private GradeMapper gradeMapper = new GradeMapper();

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
    public void testGetGradesByCulturalOfferId() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<GradeDTO>> responseEntity =
                restTemplate.exchange("/g/culturaloffer/grades/" + 14, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<GradeDTO>>() {});

        List<GradeDTO> grades = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, grades.size());
    }

    @Test
    public void testGetGradesByUserId() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<GradeDTO>> responseEntity =
                restTemplate.exchange("/g/user/grades/" + 1001, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<GradeDTO>>() {});

        List<GradeDTO> grades = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, grades.size());
    }

    @Test
    public void testAddNewGrade() {
        GradeDTO gradeDTO = new GradeDTO(
                888,
                2,
                new Date(),
                14,
                "Cvetni Konaci",
                1001,
                "perica"
        );

        HttpEntity<GradeDTO> httpEntity = new HttpEntity<GradeDTO>(gradeDTO, httpHeaders);
        int size = gradeService.findByCulturalOfferId(14).size();

        ResponseEntity<GradeDTO> responseEntity =
                restTemplate.exchange("/g",
                        HttpMethod.POST, httpEntity, GradeDTO.class);

        GradeDTO grade = responseEntity.getBody();
        assertNotNull(grade);
        assertEquals(2, grade.getValue());

        List<Grade> grades = gradeService.findByCulturalOfferId(14);
        assertEquals(size + 1, grades.size());

        gradeService.deleteById(grade.getId());
    }

    @Test
    public void testDeleteGrade() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        GradeDTO gradeDTO = new GradeDTO(
                888,
                2,
                new Date(),
                14,
                "Cvetni Konaci",
                1001,
                "perica"
        );

        gradeDTO = gradeService.addGrade(gradeDTO);

        int size = gradeService.findByCulturalOfferId(14).size();

        ResponseEntity<Map<String, Boolean>> responseEntity =
                restTemplate.exchange("/g/grade/" + gradeDTO.getId(),
                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<Map<String, Boolean>>(){});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, gradeService.findByCulturalOfferId(14).size());
    }

    @Test
    public void testUpdateGrade() {
        List<Grade> grade = gradeService.findByUserId(1001);
        Grade toBeChanged = grade.get(0);

        toBeChanged.setValue(2);
        GradeDTO dto = gradeMapper.toDto(toBeChanged);
        HttpEntity<GradeDTO> httpEntity = new HttpEntity<GradeDTO>(dto, httpHeaders);
        ResponseEntity<GradeDTO> responseEntity =
                restTemplate.exchange("/g/grade/" + toBeChanged.getId(), HttpMethod.PUT, httpEntity, GradeDTO.class);

        GradeDTO gradeChanged = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(gradeChanged);
        assertEquals(new Integer(1), gradeChanged.getId());
        assertEquals(2, gradeChanged.getValue());


        gradeChanged.setValue(4);
        responseEntity =
                restTemplate.exchange("/guests/" + 1, HttpMethod.PUT, httpEntity, GradeDTO.class);
    }
}
