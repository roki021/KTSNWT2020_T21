package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-1.properties")
public class GuestServiceUnitTest {

    @Autowired
    private GuestService guestService;

    @MockBean
    private GuestRepository guestRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD);
        Guest createdGuest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD);
        createdGuest.setId(NEW_GUEST_ID);

        given(guestRepository.findById(NEW_GUEST_ID)).willReturn(Optional.of(createdGuest));
        given(userRepository.findByUsername(NEW_GUEST_USERNAME)).willReturn(null);
        given(userRepository.findByEmailAddress(NEW_GUEST_EMAIL_ADDRESS)).willReturn(null);
        given(guestRepository.save(guest)).willReturn(createdGuest);

        Guest foundGuest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                DB_GUEST_EMAIL_ADDRESS,
                DB_GUEST_USERNAME,
                NEW_GUEST_PASSWORD);
        foundGuest.setId(NEW_GUEST_ID);
        given(userRepository.findByUsername(DB_GUEST_USERNAME)).willReturn(foundGuest);
        given(userRepository.findByEmailAddress(DB_GUEST_EMAIL_ADDRESS)).willReturn(foundGuest);

        doNothing().when(guestRepository).delete(createdGuest);
    }

    @Test
    public void testInsertOk() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest created = guestService.insert(guest);

        verify(userRepository, times(1)).findByUsername(NEW_GUEST_USERNAME);
        verify(userRepository, times(1)).findByEmailAddress(NEW_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(1)).save(guest);

        assertEquals(NEW_GUEST_USERNAME, created.getUsername());
    }

    @Test
    public void testInsertGivenNameExist() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                DB_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest created = guestService.insert(guest);

        verify(userRepository, times(1)).findByUsername(DB_GUEST_USERNAME);
        verify(userRepository, times(0)).findByEmailAddress(NEW_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(0)).save(guest);

        assertNull(created);
    }

    @Test
    public void testInsertGivenEmailExist() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                DB_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest created = guestService.insert(guest);

        verify(userRepository, times(1)).findByUsername(NEW_GUEST_USERNAME);
        verify(userRepository, times(1)).findByEmailAddress(DB_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(0)).save(guest);

        assertNull(created);
    }

    @Test
    public void testUpdateOk() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest updatedGuest = guestService.update(NEW_GUEST_ID, guest);

        verify(guestRepository, times(1)).findById(NEW_GUEST_ID);
        verify(userRepository, times(1)).findByUsername(NEW_GUEST_USERNAME);
        verify(userRepository, times(1)).findByEmailAddress(NEW_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(1)).save(guest);

        assertEquals(NEW_GUEST_USERNAME, guest.getUsername());
        assertEquals(NEW_GUEST_EMAIL_ADDRESS, guest.getEmailAddress());
    }

    @Test
    public void testUpdateGivenUsernameExist() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                DB_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest updatedGuest = guestService.update(NEW_GUEST_ID, guest);

        verify(guestRepository, times(1)).findById(NEW_GUEST_ID);
        verify(userRepository, times(1)).findByUsername(DB_GUEST_USERNAME);
        verify(userRepository, times(0)).findByEmailAddress(NEW_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(0)).save(guest);

        assertNull(updatedGuest);
    }

    @Test
    public void testUpdateGivenEmailExist() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                DB_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest updatedGuest = guestService.update(NEW_GUEST_ID, guest);

        verify(guestRepository, times(1)).findById(NEW_GUEST_ID);
        verify(userRepository, times(1)).findByUsername(NEW_GUEST_USERNAME);
        verify(userRepository, times(1)).findByEmailAddress(DB_GUEST_EMAIL_ADDRESS);
        verify(guestRepository, times(0)).save(guest);

        assertNull(updatedGuest);
    }

    @Test
    public void testDeleteOk() {
        boolean deleted = guestService.delete(NEW_GUEST_ID);

        verify(guestRepository, times(1)).findById(NEW_GUEST_ID);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteNoId() {
        boolean deleted = guestService.delete(DB_GUEST_ID);

        verify(guestRepository, times(1)).findById(DB_GUEST_ID);

        assertFalse(deleted);
    }
}
