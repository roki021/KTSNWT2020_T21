package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Subtype;
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

import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-types.properties")
public class SubtypeServiceIntegrationTest {
    @Autowired
    private SubtypeService subtypeService;

    @Test
    public void testFindAll(){
        List<Subtype> subtypes = subtypeService.findAll();
        assertEquals(TOTAL_DB_SUBTYPES, subtypes.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Subtype> subtypes = subtypeService.findAll(DB_OFFER_TYPE_ID, pageable);
        assertEquals(PAGE_SIZE, subtypes.getNumberOfElements());
    }

    @Test
    public void okTestFindOne(){
        Subtype subtypes = subtypeService.findOne(VALID_DB_SUBTYPE_ID);
        assertEquals(VALID_DB_SUBTYPE_ID, subtypes.getId());
    }

    @Test
    public void failTestFindOne(){
        Subtype subtypes = subtypeService.findOne(INVALID_DB_SUBTYPE_ID);
        assertNull(subtypes);
    }

    @Test
    public void okTestFindByName(){
        Subtype subtypes = subtypeService.findByName(DB_SUBTYPE);
        assertEquals(DB_SUBTYPE, subtypes.getName());
    }

    @Test
    public void failTestFindByName(){
        Subtype subtypes = subtypeService.findByName(NOT_DB_SUBTYPE);
        assertNull(subtypes);
    }

    @Test
    public void okTestFindByOfferType(){
        List<Subtype> subtypes = subtypeService.findByOfferType(DB_OFFER_TYPE_ID);
        assertEquals(SUBTYPES_FOR_OFFER_NUMBER, subtypes.size());
    }

    @Test
    public void failTestFindByOfferType(){
        List<Subtype> subtypes = subtypeService.findByOfferType(NOT_DB_OFFER_TYPE_ID);
        assertEquals(EMPTY, subtypes.size());
    }

    @Test
    public void okTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(NEW_SUBTYPE_NAME);

        Subtype created = subtypeService.create(subtype, OFFER_TYPE);

        assertEquals(NEW_SUBTYPE_NAME, created.getName());
    }

    @Test(expected = Exception.class)
    public void hasNameTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);

        Subtype created = subtypeService.create(subtype, OFFER_TYPE);

        assertNull(created);
    }

    @Test(expected = Exception.class)
    public void offerNotFoundTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(FAKE_SUBTYPE_NAME);

        Subtype created = subtypeService.create(subtype, NOT_OFFER_TYPE);

        assertNull(created);
    }

    @Test
    public void okTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(UPDATE_SUBTYPE_NAME);

        Subtype updated = subtypeService.update(subtype, VALID_DB_SUBTYPE_ID);

        assertEquals(UPDATE_SUBTYPE_NAME, updated.getName());
    }

    @Test(expected = Exception.class)
    public void hasNameTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);

        Subtype updated = subtypeService.update(subtype, SUBTYPE_ID);

        assertNull(updated);
    }

    @Test(expected = Exception.class)
    public void notFoundTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(FAKE_SUBTYPE_NAME);

        Subtype updated = subtypeService.update(subtype, INVALID_DB_SUBTYPE_ID);

        assertNull(updated);
    }

    @Test
    @Transactional
    public void okTestDelete() throws Exception{
        subtypeService.delete(SUBTYPE_ID);

        assertNull(subtypeService.findOne(SUBTYPE_ID));
    }

    @Test(expected = Exception.class)
    @Transactional
    public void hasOfferTestDelete() throws Exception{
        subtypeService.delete(HAS_OFFER_SUBTYPE_ID);

        assertNotNull(subtypeService.findOne(HAS_OFFER_SUBTYPE_ID));
    }

    @Test(expected = Exception.class)
    @Transactional
    public void notFoundTestDelete() throws Exception{
        subtypeService.delete(INVALID_DB_SUBTYPE_ID);
    }
}
