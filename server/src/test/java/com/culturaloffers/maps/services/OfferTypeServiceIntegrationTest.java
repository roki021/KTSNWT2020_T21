package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.OfferType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferTypeServiceIntegrationTest {
    @Autowired
    OfferTypeService offerTypeService;

    @Test
    public void testFindAll(){
        List<OfferType> offerTypes = offerTypeService.findAll();
        assertEquals(TOTAL_ELEMENTS, offerTypes.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<OfferType> offerTypes = offerTypeService.findAll(pageable);
        assertEquals(TOTAL_ELEMENTS, offerTypes.getNumberOfElements());
    }

    @Test
    public void okTestFindOne(){
        OfferType offerType = offerTypeService.findOne(VALID_DB_OFFER_TYPE_ID);
        assertEquals(VALID_DB_OFFER_TYPE_ID, offerType.getId());
    }

    @Test
    public void failTestFindOne(){
        OfferType offerType = offerTypeService.findOne(INVALID_DB_OFFER_TYPE_ID);
        assertNull(offerType);
    }

    @Test
    public void okTestFindByName(){
        OfferType offerType = offerTypeService.findOneName(DB_OFFER_TYPE);
        assertEquals(DB_OFFER_TYPE, offerType.getName());
    }

    @Test
    public void failTestFindByName(){
        OfferType offerType = offerTypeService.findOneName(NOT_DB_OFFER_TYPE);
        assertNull(offerType);
    }

    @Test
    public void okTestCreate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE);

        OfferType created = offerTypeService.create(offerType);

        assertEquals(NEW_OFFER_TYPE, created.getName());
    }

    @Test(expected = Exception.class)
    public void hasNameTestCreate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(DB_OFFER_TYPE);

        OfferType created = offerTypeService.create(offerType);

        assertNull(created);
    }


    @Test
    public void okTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(UPDATE_OFFER_TYPE_NAME);

        OfferType updated = offerTypeService.update(offerType, VALID_DB_OFFER_TYPE_ID);

        assertEquals(UPDATE_OFFER_TYPE_NAME, updated.getName());
    }

    @Test(expected = Exception.class)
    public void hasNameTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(DB_OFFER_TYPE);

        OfferType updated = offerTypeService.update(offerType, OFFER_ID);

        assertNull(updated);
    }

    @Test(expected = Exception.class)
    public void notFoundTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(FAKE_NAME);

        OfferType updated = offerTypeService.update(offerType, INVALID_DB_OFFER_TYPE_ID);

        assertNull(updated);
    }

    @Test
    @Transactional
    public void okTestDelete() throws Exception{
        offerTypeService.delete(OFFER_ID);

        assertNull(offerTypeService.findOne(OFFER_ID));
    }

    @Test(expected = Exception.class)
    @Transactional
    public void hasOfferTestDelete() throws Exception{
        offerTypeService.delete(VALID_DB_OFFER_TYPE_ID);

        assertNotNull(offerTypeService.findOne(VALID_DB_OFFER_TYPE_ID));
    }

    @Test(expected = Exception.class)
    @Transactional
    public void notFoundTestDelete() throws Exception{
        offerTypeService.delete(INVALID_DB_OFFER_TYPE_ID);
    }
}
