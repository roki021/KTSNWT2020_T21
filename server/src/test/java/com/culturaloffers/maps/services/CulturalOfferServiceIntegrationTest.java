package com.culturaloffers.maps.services;

import static com.culturaloffers.maps.constants.CulturalOfferConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.culturaloffers.maps.dto.ZoomDTO;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.GeoLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-offer.properties")
public class CulturalOfferServiceIntegrationTest {

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

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOfferWithNonUniqueName() throws Exception{
        GeoLocation geo = new GeoLocation(12.11, 13.11, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOffer offer = new CulturalOffer(null, CO_TITLE, CO_NEW_DESCRIPTION, imgs);
        service.create(offer, CO_NEW_ADDRESS, CO_SUBTYPE);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOfferWithInvalidGeoLocation() throws Exception{
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOffer offer = new CulturalOffer(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, imgs);
        service.create(offer, CO_NEW_ADDRESS, CO_SUBTYPE);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOfferWithInvalidSubtype() throws Exception{
        GeoLocation geo = new GeoLocation(12.11, 13.11, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOffer offer = new CulturalOffer(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, imgs);
        service.create(offer, CO_NEW_ADDRESS, "invalid subtype");
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testCreateCulturalOfferWithBlankDesc() throws Exception{
        GeoLocation geo = new GeoLocation(12.11, 13.11, CO_NEW_ADDRESS);
        geoLocationService.insert(geo);
        ArrayList<String> imgs = new ArrayList<>();
        CulturalOffer offer = new CulturalOffer(null, CO_TITLE, "  ", imgs);
        service.create(offer, CO_NEW_ADDRESS, CO_SUBTYPE);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testUpdateCulturalOfferWithInvalidId() throws Exception{
        List<String> images = new ArrayList<>();
        CulturalOffer updated = new CulturalOffer(null, CO_NEW_TITLE, CO_NEW_DESCRIPTION, images);
        service.update(50, updated);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testUpdateCulturalOfferWithNonUniqueName() throws Exception{
        List<String> images = new ArrayList<>();
        CulturalOffer updated = new CulturalOffer(null, CO_TAKEN_TITLE, CO_NEW_DESCRIPTION, images);
        service.update(CO_ID, updated);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testRemoveCulturalOfferWithInvalidId() throws Exception {
        service.delete(50);
    }

    @Test
    public void testGetAllInCurrentZoom() {
        ZoomDTO zoomDTO = new ZoomDTO(
                UPPER_LATITUDE,
                UPPER_LONGITUDE,
                LOWER_LATITUDE,
                LOWER_LONGITUDE
        );

        List<CulturalOffer> culturalOffers = service.getAllInCurrentZoom(zoomDTO);

        assertThat(culturalOffers.size()).isEqualTo(EXPECTED_OFFERS);
        assertThat(culturalOffers.get(ROW_NUM).getGeoLocation().getLongitude())
                .isBetween(UPPER_LONGITUDE, LOWER_LONGITUDE);
    }

}
