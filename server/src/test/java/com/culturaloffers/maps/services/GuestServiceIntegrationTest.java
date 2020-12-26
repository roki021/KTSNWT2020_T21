package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
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

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class GuestServiceIntegrationTest {

    @Autowired
    private GuestService guestService;

    @Test
    public void testGetUserByUsername() {
        Guest user = guestService.getGuestByUsername(DB_GUEST_USERNAME);
        assertEquals(DB_GUEST_USERNAME, user.getUsername());
    }

    @Test
    public void testGetUserById() {
        Guest user = guestService.getGuestById(DB_GUEST_ID);
        assertEquals(DB_GUEST_ID, user.getId());
    }

    @Test
    public void testGetAllUsers() {
        List<Guest> users = guestService.getAllGuests();
        assertEquals(DB_GUEST_ALL, users.size());
    }

    @Test
    public void testGetUsers() {
        List<Guest> users = guestService.getGuests(PageRequest.of(DB_USER_PAGE, DB_USER_SIZE)).getContent();
        assertEquals(DB_GUEST_EXPECTED, users.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteOk() {
        boolean deleted = guestService.delete(DB_GUEST_ID);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteAdminFail() {
        boolean deleted = guestService.delete(DB_ADMIN_ID);
        assertFalse(deleted);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertOk() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest created = guestService.insert(guest);

        assertEquals(NEW_GUEST_USERNAME, created.getUsername());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertFailAdminUserName() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                DB_ADMIN_USERNAME,
                NEW_GUEST_PASSWORD
        );
        Guest created = guestService.insert(guest);

        assertNull(created);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateOk() {
        Guest guest = new Guest(
            NEW_GUEST_FIRST_NAME,
            NEW_GUEST_LAST_NAME,
            NEW_GUEST_EMAIL_ADDRESS,
            NEW_GUEST_USERNAME,
            NEW_GUEST_PASSWORD
        );

        Guest updatedGuest = guestService.update(DB_GUEST_ID, guest);
        assertEquals(NEW_GUEST_USERNAME, guest.getUsername());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateFailExistUsername() {
        Guest guest = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                DB_ADMIN_USERNAME,
                NEW_GUEST_PASSWORD
        );

        Guest updatedGuest = guestService.update(DB_GUEST_ID, guest);
        assertNull(updatedGuest);
    }
}
