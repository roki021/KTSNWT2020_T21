package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.GeoLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.GeoLocationConstants.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test-1.properties")
public class GeoLocationRepositoryIntegrationTest {

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @Test
    public void testfindByAddress() {
        GeoLocation geoLocation = geoLocationRepository.findByAddress(DB_GEO_LOCATION_ADDRESS);
        assertEquals(DB_GEO_LOCATION_ADDRESS, geoLocation.getAddress());
    }

    @Test
    public void testfindByLatitudeAndLongitude() {
        GeoLocation geoLocation = geoLocationRepository.findByLatitudeAndLongitude(
                DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE
        );
        assertEquals(DB_GEO_LOCATION_LATITUDE, geoLocation.getLatitude(), DELTA);
        assertEquals(DB_GEO_LOCATION_LONGITUDE, geoLocation.getLongitude(), DELTA);
    }

    @Test
    public void testFindAll() {
        List<GeoLocation> geoLocations = geoLocationRepository
                .findAll(PageRequest.of(DB_GEO_PAGE, DB_GEO_SIZE)).getContent();
        assertEquals(DB_GEO_EXPECTED, geoLocations.size());
    }
}
