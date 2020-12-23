package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.repositories.SubtypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

//import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubtypeServiceUnitTest {
    @MockBean
    SubtypeRepository subtypeRepository;

    @MockBean
    OfferTypeService offerTypeService;

    @Autowired
    SubtypeService subtypeService;

    @Before
    public void setup(){
        OfferType offerType = new OfferType();
        offerType.setName(OFFER_TYPE);
        offerType.setId(DB_OFFER_TYPE_ID);
        given(offerTypeService.findOneName(OFFER_TYPE)).willReturn(offerType);
        given(offerTypeService.findOneName(NOT_OFFER_TYPE)).willReturn(null);

        Subtype subtypeTest = new Subtype();
        subtypeTest.setName(DB_SUBTYPE);
        subtypeTest.setOfferType(offerType);

        Subtype savedSubtype = new Subtype();
        savedSubtype.setName(DB_SUBTYPE);
        savedSubtype.setId(VALID_DB_SUBTYPE_ID);
        savedSubtype.setOfferType(offerType);

        given(subtypeRepository.findById(INVALID_DB_SUBTYPE_ID)).willReturn(java.util.Optional.empty());

        given(subtypeRepository.findById(VALID_DB_SUBTYPE_ID)).willReturn(java.util.Optional.of(savedSubtype));

        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);

        Subtype updatedSubtype = new Subtype();
        updatedSubtype.setName(SUBTYPE_NAME);
        updatedSubtype.setId(VALID_DB_SUBTYPE_ID);
        updatedSubtype.setOfferType(offerType);

        given(subtypeRepository.findByName(DB_SUBTYPE)).willReturn(null);

        given(subtypeRepository.findByName(SUBTYPE_NAME)).willReturn(subtype);

        given(subtypeRepository.findByNameAndIdNot(SUBTYPE_NAME,VALID_DB_SUBTYPE_ID)).willReturn(null);
        given(subtypeRepository.findByNameAndIdNot(SUBTYPE_NAME,SUBTYPE_ID)).willReturn(subtype);
        given(subtypeRepository.save(subtypeTest)).willReturn(savedSubtype);

        given(subtypeRepository.save(subtype)).willReturn(updatedSubtype);
        subtype.setOfferType(offerType);
        CulturalOffer culturalOffer = new CulturalOffer();
        culturalOffer.setSubtype(subtype);
        culturalOffer.setTitle(OFFER_TITLE);
        culturalOffer.setDescription(OFFER_DES);
        GeoLocation location = new GeoLocation();
        location.setLatitude(43.);
        location.setLongitude(20.);
        culturalOffer.setGeoLocation(location);
        Set<CulturalOffer> culturalOfferSet = new HashSet<CulturalOffer>();
        culturalOfferSet.add(culturalOffer);
        subtype.setCulturalOffers(culturalOfferSet);


        given(subtypeRepository.findById(SUBTYPE_ID)).willReturn(java.util.Optional.of(subtype));

        doNothing().when(subtypeRepository).delete(savedSubtype);

        Subtype failDelSubtype = new Subtype();
        failDelSubtype.setName(DB_SUBTYPE);
        failDelSubtype.setId(INVALID_DB_SUBTYPE_ID);
        doNothing().when(subtypeRepository).delete(failDelSubtype);
        doNothing().when(subtypeRepository).delete(subtype);
    }

    @Test
    public void okTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        Subtype created = subtypeService.create(subtype, OFFER_TYPE);

        verify(subtypeRepository, times(1)).findByName(DB_SUBTYPE);
        verify(offerTypeService, times(1)).findOneName(OFFER_TYPE);
        verify(subtypeRepository, times(1)).save(subtype);
        assertEquals(DB_SUBTYPE, created.getName());

    }

    @Test(expected = Exception.class)
    public void offerTypeNotFoundTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        Subtype created = subtypeService.create(subtype, NOT_OFFER_TYPE);

        verify(subtypeRepository, times(1)).findByName(DB_SUBTYPE);
        verify(offerTypeService, times(1)).findOneName(NOT_OFFER_TYPE);
        verify(subtypeRepository, times(0)).save(subtype);
        assertNull(created);

    }

    @Test(expected = Exception.class)
    public void nameExistsTestCreate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);
        Subtype created = subtypeService.create(subtype, OFFER_TYPE);

        verify(subtypeRepository, times(1)).findByName(SUBTYPE_NAME);
        verify(offerTypeService, times(0)).findOneName(OFFER_TYPE);
        verify(subtypeRepository, times(0)).save(subtype);
        assertNull(created);
    }

    @Test
    public void okTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);
        Subtype updated = subtypeService.update(subtype, VALID_DB_SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(VALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(1)).findByNameAndIdNot(SUBTYPE_NAME, VALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(1)).save(subtype);
        assertEquals(SUBTYPE_NAME, updated.getName());
    }

    @Test(expected = Exception.class)
    public void nameTakenTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);
        Subtype updated = subtypeService.update(subtype, SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(SUBTYPE_ID);
        verify(subtypeRepository, times(1)).findByNameAndIdNot(SUBTYPE_NAME, SUBTYPE_ID);
        verify(subtypeRepository, times(0)).save(subtype);
        assertNull(updated);
    }

    @Test(expected = Exception.class)
    public void notFoundTestUpdate() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);
        Subtype updated = subtypeService.update(subtype, INVALID_DB_SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(INVALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(0)).findByNameAndIdNot(SUBTYPE_NAME, INVALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(0)).save(subtype);
        assertNull(updated);
    }

    @Test
    public void okTestDelete() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        subtype.setId(VALID_DB_SUBTYPE_ID);
        subtypeService.delete(VALID_DB_SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(VALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(1)).delete(subtype);
    }

    @Test(expected = Exception.class)
    public void hasOfferTestDelete() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(SUBTYPE_NAME);
        subtype.setId(SUBTYPE_ID);
        subtypeService.delete(SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(SUBTYPE_ID);
        verify(subtypeRepository, times(0)).delete(subtype);
    }

    @Test(expected = Exception.class)
    public void notFoundTestDelete() throws Exception{
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        subtype.setId(INVALID_DB_SUBTYPE_ID);
        subtypeService.delete(INVALID_DB_SUBTYPE_ID);

        verify(subtypeRepository, times(1)).findById(INVALID_DB_SUBTYPE_ID);
        verify(subtypeRepository, times(0)).delete(subtype);
    }
}
