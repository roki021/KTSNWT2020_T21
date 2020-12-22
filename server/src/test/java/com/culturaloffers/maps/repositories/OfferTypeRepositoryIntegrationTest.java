package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.OfferType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferTypeRepositoryIntegrationTest {
    @Autowired
    OfferTypeRepository offerTypeRepository;

    @Test
    public void okTestFindByName(){
        OfferType offerType = offerTypeRepository.findByName(DB_OFFER_TYPE);
        assertEquals(DB_OFFER_TYPE, offerType.getName());
    }

    @Test
    public void failTestFindByName(){
        OfferType offerType = offerTypeRepository.findByName(NOT_DB_OFFER_TYPE);
        assertNull(offerType);
    }

    @Test
    public void okTestFindByNameAndIdNot(){
        OfferType offerType = offerTypeRepository.findByNameAndIdNot(DB_OFFER_TYPE, VALID_DB_OFFER_TYPE_ID);
        assertNull(offerType);
    }

    @Test
    public void failTestFindByNameAndIdNot(){
        OfferType offerType = offerTypeRepository.findByNameAndIdNot(DB_OFFER_TYPE, INVALID_DB_OFFER_TYPE_ID);
        assertNotNull(offerType);
    }
}
