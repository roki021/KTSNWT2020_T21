package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.GeoLocation;
import com.culturaloffers.maps.repositories.GeoLocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.culturaloffers.maps.constants.GeoLocationConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-1.properties")
public class GeoLocationServiceUnitTest {

    @Autowired
    private GeoLocationService geoLocationService;

    @MockBean
    private GeoLocationRepository geoLocationRepository;

    @Before
    public void setup() {
        GeoLocation geoLocation = new GeoLocation(
            NEW_GEO_LOCATION_LATITUDE,
            NEW_GEO_LOCATION_LONGITUDE,
            NEW_GEO_LOCATION_ADDRESS
        );
        GeoLocation createdGeoLocation = new GeoLocation(
            NEW_GEO_LOCATION_LATITUDE,
            NEW_GEO_LOCATION_LONGITUDE,
            NEW_GEO_LOCATION_ADDRESS
        );
        createdGeoLocation.setId(NEW_GEO_LOCATION_ID);

        given(geoLocationRepository.findById(NEW_GEO_LOCATION_ID)).willReturn(Optional.empty());
        given(geoLocationRepository.findByLatitudeAndLongitude(
                NEW_GEO_LOCATION_LATITUDE, NEW_GEO_LOCATION_LONGITUDE)).willReturn(null);
        given(geoLocationRepository.findByAddress(NEW_GEO_LOCATION_ADDRESS)).willReturn(null);
        given(geoLocationRepository.save(geoLocation)).willReturn(createdGeoLocation);

        GeoLocation found = new GeoLocation(
            DB_GEO_LOCATION_LATITUDE,
            DB_GEO_LOCATION_LONGITUDE,
            DB_GEO_LOCATION_ADDRESS
        );
        found.setId(DB_GEO_LOCATION_ID);
        given(geoLocationRepository.findByLatitudeAndLongitude(
                DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE)).willReturn(found);
        given(geoLocationRepository.findByAddress(DB_GEO_LOCATION_ADDRESS)).willReturn(found);
        given(geoLocationRepository.findById(DB_GEO_LOCATION_ID)).willReturn(Optional.of(found));

        doNothing().when(geoLocationRepository).delete(createdGeoLocation);
    }

    @Test
    public void testDeleteByAddressOk() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_ADDRESS);

        verify(geoLocationRepository, times(1)).findByAddress(DB_GEO_LOCATION_ADDRESS);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteByLatitudeAndLongitudeOk() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE);

        verify(geoLocationRepository, times(1))
                .findByLatitudeAndLongitude(DB_GEO_LOCATION_LATITUDE, DB_GEO_LOCATION_LONGITUDE);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteByIdOk() {
        boolean deleted = geoLocationService.delete(DB_GEO_LOCATION_ID);

        verify(geoLocationRepository, times(1)).findById(DB_GEO_LOCATION_ID);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteWrongCoords() {
        boolean deleted = geoLocationService.delete(NEW_GEO_LOCATION_LATITUDE, NEW_GEO_LOCATION_LONGITUDE);

        verify(geoLocationRepository, times(1))
                .findByLatitudeAndLongitude(NEW_GEO_LOCATION_LATITUDE, NEW_GEO_LOCATION_LONGITUDE);

        assertFalse(deleted);
    }

    @Test
    public void testDeleteWrongAddress() {
        boolean deleted = geoLocationService.delete(NEW_GEO_LOCATION_ADDRESS);

        verify(geoLocationRepository, times(1)).findByAddress(NEW_GEO_LOCATION_ADDRESS);

        assertFalse(deleted);
    }

    @Test
    public void testDeleteBadId() {
        boolean deleted = geoLocationService.delete(NEW_GEO_LOCATION_ID);

        verify(geoLocationRepository, times(1)).findById(NEW_GEO_LOCATION_ID);

        assertFalse(deleted);
    }

    @Test
    public void testUpdateOk() {
        GeoLocation geoLocation = new GeoLocation(
            NEW_GEO_LOCATION_LATITUDE,
            NEW_GEO_LOCATION_LONGITUDE,
            NEW_GEO_LOCATION_ADDRESS
        );
        GeoLocation updatedGeoLocation = geoLocationService.update(DB_GEO_LOCATION_ID, geoLocation);

        verify(geoLocationRepository, times(1)).findById(DB_GEO_LOCATION_ID);
        verify(geoLocationRepository, times(1)).findByAddress(NEW_GEO_LOCATION_ADDRESS);
        verify(geoLocationRepository, times(1)).save(geoLocation);

        assertEquals(NEW_GEO_LOCATION_ADDRESS, updatedGeoLocation.getAddress());
        assertEquals(NEW_GEO_LOCATION_LATITUDE, updatedGeoLocation.getLatitude(), DELTA);
        assertEquals(NEW_GEO_LOCATION_LONGITUDE, updatedGeoLocation.getLongitude(), DELTA);
    }

    /*@Test
    public void testUpdateSameAddress() {
        GeoLocation geoLocation = new GeoLocation(
                NEW_GEO_LOCATION_LATITUDE,
                NEW_GEO_LOCATION_LONGITUDE,
                DB_GEO_LOCATION_ADDRESS
        );
        GeoLocation updatedGeoLocation = geoLocationService.update(DB_GEO_LOCATION_ID, geoLocation);

        verify(geoLocationRepository, times(1)).findById(DB_GEO_LOCATION_ID);
        verify(geoLocationRepository, times(1)).save(geoLocation);

        assertEquals(NEW_GEO_LOCATION_ADDRESS, updatedGeoLocation.getAddress());
        assertEquals(NEW_GEO_LOCATION_LATITUDE, updatedGeoLocation.getLatitude(), DELTA);
        assertEquals(NEW_GEO_LOCATION_LONGITUDE, updatedGeoLocation.getLongitude(), DELTA);
    }*/
}
