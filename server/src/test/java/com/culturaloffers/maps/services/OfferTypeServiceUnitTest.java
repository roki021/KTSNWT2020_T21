package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.repositories.OfferTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
//import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-types.properties")
public class OfferTypeServiceUnitTest {

    @MockBean
    private OfferTypeRepository offerTypeRepository;

    @MockBean
    private SubtypeService subtypeService;

    @Autowired
    private OfferTypeService offerTypeService;

    @Before
    public void setup() {
        List<OfferType> offerTypes =  new ArrayList<>();
        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE1);
        offerTypes.add(offerType);
        OfferType offerType2 = new OfferType();
        offerType2.setName(NEW_OFFER_TYPE2);
        offerTypes.add(offerType2);
        OfferType offerType3 = new OfferType();
        offerType3.setName(NEW_OFFER_TYPE3);
        offerTypes.add(offerType3);

        given(offerTypeRepository.findAll()).willReturn(offerTypes);

        OfferType offer = new OfferType();
        offer.setName(NEW_OFFER_TYPE1);
        OfferType savedOffer = new OfferType();
        savedOffer.setName(NEW_OFFER_TYPE1);
        savedOffer.setId(OFFER_ID);

        given(offerTypeRepository.findById(OFFER_ID)).willReturn(java.util.Optional.of(savedOffer));

        given(offerTypeRepository.findById(BAD_ID)).willReturn(java.util.Optional.empty());

        given(offerTypeRepository.findByName(NEW_OFFER_TYPE1)).willReturn(null);

        OfferType offerTypeFound = new OfferType();
        offerTypeFound.setId(VALID_DB_OFFER_TYPE_ID);
        offerTypeFound.setName(DB_OFFER_TYPE);
        given(offerTypeRepository.findByName(DB_OFFER_TYPE)).willReturn(offerTypeFound);

        given(offerTypeRepository.findByNameAndIdNot(DB_OFFER_TYPE,OFFER_ID)).willReturn(offerTypeFound);
        given(offerTypeRepository.save(offer)).willReturn(savedOffer);

        List<Subtype> subtypes = new ArrayList<>();
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        subtype.setOfferType(offerTypeFound);
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
        subtypes.add(subtype);
        given(subtypeService.findByOfferType(VALID_DB_OFFER_TYPE_ID)).willReturn(subtypes);

        given(subtypeService.findByOfferType(OFFER_ID)).willReturn(new ArrayList<Subtype>());

        given(offerTypeRepository.findById(VALID_DB_OFFER_TYPE_ID)).willReturn(java.util.Optional.of(offerTypeFound));
        doNothing().when(offerTypeRepository).delete(savedOffer);
    }

    @Test
    public void okTestCreate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE1);
        OfferType created = offerTypeService.create(offerType);


        verify(offerTypeRepository, times(1)).findByName(NEW_OFFER_TYPE1);
        verify(offerTypeRepository, times(1)).save(offerType);

        assertEquals(NEW_OFFER_TYPE1, created.getName());
    }

    @Test(expected = Exception.class)
    public void nameExistsTestCreate()  throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(DB_OFFER_TYPE);
        OfferType created = null;
        created = offerTypeService.create(offerType);

        verify(offerTypeRepository, times(1)).findByName(DB_OFFER_TYPE);

        assertNull(created);
    }

    @Test
    public void okTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE1);
        OfferType created = offerTypeService.update(offerType, OFFER_ID);


        verify(offerTypeRepository, times(1)).findById(OFFER_ID);
        verify(offerTypeRepository, times(1)).findByNameAndIdNot(NEW_OFFER_TYPE1, OFFER_ID);

        assertEquals(NEW_OFFER_TYPE1, created.getName());
    }

    @Test(expected = Exception.class)
    public void nameExistsTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(DB_OFFER_TYPE);
        OfferType created = null;
        created = offerTypeService.update(offerType, OFFER_ID);

        verify(offerTypeRepository, times(1)).findById(OFFER_ID);
        verify(offerTypeRepository, times(1)).findByNameAndIdNot(DB_OFFER_TYPE, OFFER_ID);

        assertNull(created);
    }

    @Test(expected = Exception.class)
    public void notFoundTestUpdate() throws Exception{
        OfferType offerType = new OfferType();
        //offerType.setId(BAD_ID);
        offerType.setName(NEW_OFFER_TYPE1);
        OfferType created = null;
        created = offerTypeService.update(offerType, BAD_ID);

        verify(offerTypeRepository, times(1)).findById(BAD_ID);

        assertNull(created);
    }

    @Test
    public void okTestDelete() throws Exception{
        offerTypeService.delete(OFFER_ID);

        OfferType savedOfferType = new OfferType();
        savedOfferType.setName(NEW_OFFER_TYPE1);
        savedOfferType.setId(OFFER_ID);

        verify(offerTypeRepository, times(1)).findById(OFFER_ID);
        verify(offerTypeRepository, times(1)).delete(savedOfferType);
    }

    @Test(expected = Exception.class)
    public void subtypeWithOfferExistTestDelete() throws Exception{
        offerTypeService.delete(VALID_DB_OFFER_TYPE_ID);

        verify(offerTypeRepository, times(1)).findById(VALID_DB_OFFER_TYPE_ID);

        OfferType offerType = offerTypeRepository.findById(VALID_DB_OFFER_TYPE_ID).orElse(null);

        verify(offerTypeRepository, times(0)).delete(offerType);
    }

    @Test(expected = Exception.class)
    public void notFoundTestDelete() throws Exception{
        offerTypeService.delete(BAD_ID);

        verify(offerTypeRepository, times(1)).findById(BAD_ID);

        //OfferType offerType = offerTypeRepository.findById(BAD_ID).orElse(null);
    }
}
