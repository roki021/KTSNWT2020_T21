package com.culturaloffers.maps.services;

import static org.assertj.core.api.Assertions.assertThat;

import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_ID;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_ADDRESS;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_DESCRIPTION;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_NEW_ADDRESS;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_NEW_DESCRIPTION;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_NEW_TITLE;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_SUBTYPE;
import static com.culturaloffers.maps.constants.CulturalOfferConstants.CO_TITLE;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.GeoLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOfferServiceTest {

    @Autowired
    private CulturalOfferService service;

    @Autowired
    private GeoLocationService geoLocationService;

    @Test
    public void testFindAll(){
        List<CulturalOffer> all = service.findAll();
        assertThat(all).hasSize(15);
    }

    @Test
    public void testFindAllPageable(){
        PageRequest request = PageRequest.of(1, 5);
        Page<CulturalOffer> page = service.findAll(request);
        assertThat(page).hasSize(5);
    }

    @Test
    public void testFindOne(){
        CulturalOffer offer = service.findOne(CO_ID);
        assertThat(offer).isNotNull();
        assertThat(offer.getTitle()).isEqualTo(CO_TITLE);
        assertThat(offer.getDescription()).isEqualTo(CO_DESCRIPTION);
        assertThat(offer.getGeoLocation().getAddress()).isEqualTo(CO_ADDRESS);
        assertThat(offer.getSubtype().getName()).isEqualTo(CO_SUBTYPE);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOffer() throws Exception {
        int sizeBefore = service.findAll().size();
        GeoLocation location = new GeoLocation(12.12, 13.13, CO_NEW_ADDRESS);
        geoLocationService.insert(location);
        List<String> images = new ArrayList<>();
        CulturalOffer culturalOffer = new CulturalOffer(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, images);
        service.create(culturalOffer, location.getAddress(), "crkva");
        assertThat(sizeBefore).isLessThan(service.findAll().size());
        CulturalOffer testOffer = service.findOne(service.findAll().size());
        assertThat(testOffer).isNotNull();
        assertThat(testOffer.getId()).isEqualTo(culturalOffer.getId());
        assertThat(testOffer.getDescription()).isEqualTo(culturalOffer.getDescription());
        assertThat(testOffer.getTitle()).isEqualTo(culturalOffer.getTitle());
        assertThat(testOffer.getGeoLocation().getAddress()).isEqualTo(location.getAddress());
        assertThat(testOffer.getSubtype().getName()).isEqualTo("crkva");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRemoveCulturalOffer() throws Exception {
        int sizeBefore = service.findAll().size();
        CulturalOffer offer = service.findOne(CO_ID);
        assertThat(offer).isNotNull();
        service.delete(CO_ID);
        CulturalOffer delOffer = service.findOne(CO_ID);
        assertThat(delOffer).isNull();
        assertThat(service.findAll().size()).isLessThan(sizeBefore);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateCulturalOffer() throws Exception{
        int sizeBefore = service.findAll().size();
        CulturalOffer offer = service.findOne(CO_ID);
        assertThat(offer).isNotNull();
        List<String> images = new ArrayList<>();
        CulturalOffer updated = new CulturalOffer(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, images);
        updated = service.update(CO_ID, updated);
        assertThat(service.findAll().size()).isEqualTo(sizeBefore);
        assertThat(service.findOne(CO_ID)).isNotNull();
        assertThat(service.findOne(CO_ID)).isEqualTo(updated);
    }

}
