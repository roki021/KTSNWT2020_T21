package com.culturaloffers.maps.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import static com.culturaloffers.maps.constants.CulturalOfferConstants.*;
import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.GeoLocationService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-2.properties")
public class CulturalOfferControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CulturalOfferService service;

    @Autowired
    private GeoLocationService geoLocationService;

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
        ResponseEntity<CulturalOfferDTO[]> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.GET, httpEntity, CulturalOfferDTO[].class);
        CulturalOfferDTO[] dtos = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtos).isNotNull();
        assertThat(dtos).isNotEmpty();
        assertThat(dtos.length).isEqualTo(15);
    }

//    @Test
//    public void testGetAllPageable(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Token", accessToken);
//        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
//        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
//                restTemplate.exchange("/offers/by-page?page=0&size=5", HttpMethod.GET, httpEntity,
//                        new ParameterizedTypeReference<List<CulturalOfferDTO>>(){});
//        List<CulturalOfferDTO> dtos = responseEntity.getBody();
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(dtos).isNotNull();
//        assertThat(dtos).isNotEmpty();
//        assertThat(dtos.size()).isEqualTo(5);
//    }

    @Test
    public void testFindOneOffer(){
        HttpEntity httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers/"+CO_ID, HttpMethod.GET, httpEntity, CulturalOfferDTO.class);
        CulturalOfferDTO offer = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(offer).isNotNull();
        assertThat(offer.getTitle()).isEqualTo(CO_TITLE);
        assertThat(offer.getDescription()).isEqualTo(CO_DESCRIPTION);
        assertThat(offer.getAddress()).isEqualTo(CO_ADDRESS);
    }

    @Test
    public void testFindNoOffer(){
        HttpEntity httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers/"+50, HttpMethod.GET, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddOneOffer(){
        ArrayList<String> imgs = new ArrayList<>();
        GeoLocation geo = new GeoLocation(12.12, 13.54, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        CulturalOfferDTO offer = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(offer).isNotNull();
        assertThat(offer.getTitle()).isEqualTo(CO_NEW_TITLE);
        assertThat(offer.getDescription()).isEqualTo(CO_NEW_DESCRIPTION);
        assertThat(offer.getAddress()).isEqualTo(CO_NEW_ADDRESS);
    }

    @Test
    public void testAddOneOfferWithNonUniqueTitle(){
        ArrayList<String> imgs = new ArrayList<>();
        GeoLocation geo = new GeoLocation(12.12, 13.54, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneOfferWithEmptyTitle(){
        ArrayList<String> imgs = new ArrayList<>();
        GeoLocation geo = new GeoLocation(12.12, 13.54, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        CulturalOfferDTO dto = new CulturalOfferDTO(null, "", CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneOfferWithEmptyDescription(){
        ArrayList<String> imgs = new ArrayList<>();
        GeoLocation geo = new GeoLocation(12.12, 13.54, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_NEW_TITLE, "", CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneOfferWithInvalidGeoLocation(){
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddOneOfferWithInvalidSubtype(){
        ArrayList<String> imgs = new ArrayList<>();
        GeoLocation geo = new GeoLocation(12.12, 13.54, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, "invalid", imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateOffer(){
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_UP_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers/"+CO_UP_ID, HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
        CulturalOfferDTO offer = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(offer).isNotNull();
        assertThat(offer.getId()).isEqualTo(CO_UP_ID);
        assertThat(offer.getTitle()).isEqualTo(CO_UP_TITLE);
        assertThat(offer.getDescription()).isEqualTo(CO_NEW_DESCRIPTION);
    }

    @Test
    public void testUpdateOfferWithInvalidId(){
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_UP_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers/"+40, HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateOfferWithNonUniqueTitle(){
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOfferDTO dto = new CulturalOfferDTO(null, CO_TITLE, CO_NEW_DESCRIPTION, CO_NEW_ADDRESS, CO_SUBTYPE, imgs);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);
        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/offers/"+CO_UP_ID, HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteOffer(){
        int sizeBefore = service.findAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/offers/"+CO_DEL_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(service.findOne(CO_DEL_ID)).isNull();
        assertThat(service.findAll().size()).isLessThan(sizeBefore);
    }

    @Test
    public void testDeleteOfferWithInvalidId(){
        int sizeBefore = service.findAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/offers/"+45, HttpMethod.DELETE, httpEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
