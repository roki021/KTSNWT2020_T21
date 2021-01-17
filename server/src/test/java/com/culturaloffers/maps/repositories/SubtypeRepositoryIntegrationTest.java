package com.culturaloffers.maps.repositories;

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

import java.util.List;

import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-types.properties")
public class SubtypeRepositoryIntegrationTest {

    @Autowired
    private SubtypeRepository subtypeRepository;

    @Test
    public void okTestFindByName(){
        Subtype subtype = subtypeRepository.findByName(DB_SUBTYPE);
        assertEquals(DB_SUBTYPE, subtype.getName());
    }

    @Test
    public void failTestFindByName(){
        Subtype subtype = subtypeRepository.findByName(NOT_DB_SUBTYPE);
        assertNull(subtype);
    }

    @Test
    public void okTestFindByNameAndIdNot(){
        Subtype subtype = subtypeRepository.findByNameAndIdNot(DB_SUBTYPE, VALID_DB_SUBTYPE_ID);
        assertNull(subtype);
    }

    @Test
    public void failTestFindByNameAndIdNot(){
        Subtype subtype = subtypeRepository.findByNameAndIdNot(DB_SUBTYPE, INVALID_DB_SUBTYPE_ID);
        assertNotNull(subtype);
    }

    @Test
    public void okTestFindByOfferTypeId(){
        List<Subtype> subtype = subtypeRepository.findByOfferTypeId(DB_OFFER_TYPE_ID);
        assertEquals(2, subtype.size());
    }

    @Test
    public void failTestFindByOfferTypeId(){
        List<Subtype> subtype = subtypeRepository.findByOfferTypeId(NOT_DB_OFFER_TYPE_ID);
        assertEquals(0, subtype.size());
    }

    @Test
    public void testFindAllByOfferTypeId(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Subtype> subtypes = subtypeRepository.findAllByOfferTypeId(DB_OFFER_TYPE_ID, pageable);
        assertEquals(PAGE_SIZE, subtypes.getNumberOfElements());
    }

    @Test
    public void failFindAllByOfferTypeId(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Subtype> subtypes = subtypeRepository.findAllByOfferTypeId(NOT_DB_OFFER_TYPE_ID, pageable);
        assertEquals(0, subtypes.getNumberOfElements());
    }
}
