package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CulturalOfferDTO;
import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.SearchDTO;
import com.culturaloffers.maps.services.CulturalOfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.SearchConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-search.properties")
public class SearchControllerIntegrationTest {
    @Autowired
    private CulturalOfferService culturalOfferService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void okTestSearchCulturalOfferTitle() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_TITLE);
        searchDTO.setSearchValue(TITLE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_TITLE, offers.size());
        assertTrue(offers.get(0).getTitle().contains(TITLE));
    }

    @Test
    public void notFoundTestSearchCulturalOfferTitle() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_TITLE);
        searchDTO.setSearchValue(BAD_TITLE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
        assertTrue(offers.isEmpty());
    }

    @Test
    public void okTestSearchCulturalOfferDescription() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_DESCRIPTION);
        searchDTO.setSearchValue(DESCRIPTION);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_DESCRIPTION, offers.size());
        assertTrue(offers.get(0).getDescription().contains(DESCRIPTION));
    }

    @Test
    public void notFoundTestSearchCulturalOfferDescription() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_DESCRIPTION);
        searchDTO.setSearchValue(BAD_DESCRIPTION);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
        assertTrue(offers.isEmpty());
    }

    @Test
    public void okTestSearchCulturalOfferSubtype() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_SUBTYPE);
        searchDTO.setSearchValue(SUBTYPE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_SUBTYPE, offers.size());
        assertTrue(offers.get(0).getSubTypeName().contains(SUBTYPE));
    }

    @Test
    public void notFoundTestSearchCulturalOfferSubtype() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_SUBTYPE);
        searchDTO.setSearchValue(BAD_SUBTYPE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
        assertTrue(offers.isEmpty());
    }

    @Test
    public void okTestSearchCulturalOfferType() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_TYPE);
        searchDTO.setSearchValue(TYPE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_TYPE, offers.size());
    }

    @Test
    public void notFoundTestSearchCulturalOfferType() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_TYPE);
        searchDTO.setSearchValue(BAD_TYPE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
        assertTrue(offers.isEmpty());
    }

    @Test
    public void okTestSearchCulturalOfferSubscribers() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_SUBSCRIBERS);
        searchDTO.setSearchValue(SUBSCRIBERS);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_SUBSCRIBERS, offers.size());
    }

    @Test
    public void notFoundTestSearchCulturalOfferSubscribers() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_SUBSCRIBERS);
        searchDTO.setSearchValue(BAD_SUBSCRIBERS);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
    }

    @Test
    public void invalidTestSearchCulturalOfferSubscribers() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_SUBSCRIBERS);
        searchDTO.setSearchValue(INVALID_SUBSCRIBERS);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(offers);
    }

    @Test
    public void okTestSearchCulturalOfferGrade() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_GRADE);
        searchDTO.setSearchValue(GRADE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECT_GRADE, offers.size());
    }

    @Test
    public void notFoundTestSearchCulturalOfferGrade() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_GRADE);
        searchDTO.setSearchValue(BAD_GRADE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, offers.size());
    }

    @Test
    public void invalidTestSearchCulturalOfferGrade() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_GRADE);
        searchDTO.setSearchValue(INVALID_GRADE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(offers);
    }

    @Test
    public void notPositiveTestSearchCulturalOfferGrade() {

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchField(SEARCH_GRADE);
        searchDTO.setSearchValue(NEGATIVE_GRADE);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(searchDTO);
        ResponseEntity<List<CulturalOfferDTO>> responseEntity =
                restTemplate.exchange("/offers/search", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<CulturalOfferDTO>>() {});

        List<CulturalOfferDTO> offers = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(offers);
    }
}
