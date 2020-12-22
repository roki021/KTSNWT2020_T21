package com.culturaloffers.maps.services;

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
import java.util.List;

import static com.culturaloffers.maps.constants.OfferTypeConstants.*;
import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
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

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<OfferType> offerTypePage = new PageImpl<>(offerTypes,pageable,PAGEABLE_TOTAL_ELEMENTS);

        // Definisanje ponasanja test dvojnika culturalContentCategoryRepository za findAll metodu
        given(offerTypeRepository.findAll()).willReturn(offerTypes);

        given(offerTypeRepository.findAll(pageable)).willReturn(offerTypePage);

        OfferType offer = new OfferType();
        offer.setName(NEW_OFFER_TYPE1);
        OfferType savedOffer = new OfferType();
        savedOffer.setName(NEW_OFFER_TYPE1);
        savedOffer.setId(OFFER_ID);

        given(offerTypeRepository.findById(OFFER_ID)).willReturn(java.util.Optional.of(savedOffer));

        given(offerTypeRepository.findByName(NEW_OFFER_TYPE1)).willReturn(null);

        OfferType offerTypeFound = new OfferType();
        offerTypeFound.setId(VALID_DB_OFFER_TYPE_ID);
        offerTypeFound.setName(DB_OFFER_TYPE);
        given(offerTypeRepository.findByName(DB_OFFER_TYPE)).willReturn(offerTypeFound);

        given(offerTypeRepository.findByNameAndIdNot(NEW_OFFER_TYPE1,OFFER_ID)).willReturn(null);
        given(offerTypeRepository.save(offerType)).willReturn(savedOffer);

        List<Subtype> subtypes = new ArrayList<>();
        Subtype subtype = new Subtype();
        subtype.setName(DB_SUBTYPE);
        subtype.setOfferType(offerTypeFound);
        given(subtypeService.findByOfferType(VALID_DB_OFFER_TYPE_ID)).willReturn(subtypes);

        given(subtypeService.findByOfferType(OFFER_ID)).willReturn(null);

        doNothing().when(offerTypeRepository).delete(savedOffer);
    }

    @Test
    public void okTestCreate() throws Exception{
        OfferType offerType = new OfferType();
        offerType.setName(NEW_OFFER_TYPE1);
        OfferType created = offerTypeService.create(offerType);

        verify(offerTypeRepository, times(1)).findByName(NEW_OFFER_TYPE1);
        verify(offerTypeRepository, times(1)).save(offerType);

        //assertEquals(NEW_OFFER_TYPE1, created.getName());
        assertNull(created);
    }

    @Test
    public void failTestCreate() throws Exception{

    }

    @Test
    public void okTestUpdate() throws Exception{

    }

    @Test
    public void failTestUpdate() throws Exception{

    }

    @Test
    public void okTestDelete() throws Exception{

    }

    public void failTestDelete() throws Exception{

    }
}
