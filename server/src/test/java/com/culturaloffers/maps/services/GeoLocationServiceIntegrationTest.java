package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.GeoLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static com.culturaloffers.maps.constants.GeoLocationConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-1.properties")
public class GeoLocationServiceIntegrationTest {

    @Autowired
    private GeoLocationService geoLocationService;

    @Test
    public void testGetByAddress() {
        GeoLocation geoLocation = geoLocationService.getByAddress(DB_GEO_LOCATION_ADDRESS);
        assertEquals(DB_GEO_LOCATION_ADDRESS, geoLocation.getAddress());
    }

    @Test
    public void testGetByWrongAddress() {
        GeoLocation geoLocation = geoLocationService.getByAddress(NEW_GEO_LOCATION_ADDRESS);
        assertNull(geoLocation);
    }

    @Test
    public void testGetByLatitudeAndLongitude() {
        GeoLocation geoLocation = geoLocationService.getByLatitudeAndLongitude(
                DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE);
        assertEquals(DB_GEO_LOCATION_LATITUDE, geoLocation.getLatitude(), DELTA);
        assertEquals(DB_GEO_LOCATION_LONGITUDE, geoLocation.getLongitude(), DELTA);
    }

    @Test
    public void testGetByLatitudeAndLongitudeWrong() {
        GeoLocation geoLocation = geoLocationService.getByLatitudeAndLongitude(
                DB_GEO_LOCATION_LATITUDE, NEW_GEO_LOCATION_LONGITUDE);
        assertNull(geoLocation);
    }

    @Test
    public void testGetAllGeoLocations() {
        List<GeoLocation> geoLocations = geoLocationService.getAllGeoLocations();
        assertEquals(DB_GEO_ALL, geoLocations.size());
    }

    @Test
    public void testGetGeoLocationsFirstPage() {
        List<GeoLocation> geoLocations = geoLocationService.getGeoLocations(
                PageRequest.of(DB_GEO_PAGE, DB_GEO_SIZE)).getContent();
        assertEquals(DB_GEO_SIZE, geoLocations.size());
    }

    @Test
    public void testGetGeoLocationsSecondPage() {
        List<GeoLocation> geoLocations = geoLocationService.getGeoLocations(
                PageRequest.of(DB_GEO_PAGE_TWO, DB_GEO_SIZE)).getContent();
        assertEquals(DB_GEO_EXPECTED_TWO, geoLocations.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteByAddress() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_ADDRESS);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteByLatitudeAndLongitude() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteById() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_ID);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsert() {
        GeoLocation geoLocation = new GeoLocation(
                NEW_GEO_LOCATION_LATITUDE,
                NEW_GEO_LOCATION_LONGITUDE,
                NEW_GEO_LOCATION_ADDRESS
        );

        GeoLocation created = geoLocationService.insert(geoLocation);

        assertEquals(NEW_GEO_LOCATION_ADDRESS, created.getAddress());
        assertEquals(NEW_GEO_LOCATION_LATITUDE, created.getLatitude(), DELTA);
        assertEquals(NEW_GEO_LOCATION_LONGITUDE, created.getLongitude(), DELTA);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        GeoLocation geoLocation = new GeoLocation(
                NEW_GEO_LOCATION_LATITUDE,
                NEW_GEO_LOCATION_LONGITUDE,
                NEW_GEO_LOCATION_ADDRESS
        );

        GeoLocation updated = geoLocationService.update(DB_GEO_LOCATION_ID, geoLocation);
        assertEquals(NEW_GEO_LOCATION_ADDRESS, updated.getAddress());
        assertEquals(NEW_GEO_LOCATION_LATITUDE, updated.getLatitude(), DELTA);
        assertEquals(NEW_GEO_LOCATION_LONGITUDE, updated.getLongitude(), DELTA);
    }
}
