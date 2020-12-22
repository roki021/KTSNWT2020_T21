package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test-1.properties")
public class GuestRepositoryIntegrationTest {

    @Autowired
    private GuestRepository guestRepository;

    @Test
    public void testFindByUsername() {
        Guest guest = guestRepository.findByUsername(DB_GUEST_USERNAME);
        assertEquals(DB_GUEST_USERNAME, guest.getUsername());
    }

    @Test
    public void testFindByEmailAddress() {
        Guest guest = guestRepository.findByEmailAddress(DB_GUEST_EMAIL_ADDRESS);
        assertEquals(DB_GUEST_EMAIL_ADDRESS, guest.getEmailAddress());
    }

    @Test
    public void testFindAll() {
        List<Guest> guests = guestRepository.findAll(PageRequest.of(DB_USER_PAGE, DB_USER_SIZE)).getContent();
        assertEquals(DB_GUEST_EXPECTED, guests.size());
    }

    @Test
    public void testFindByUsernameAdmin() {
        Guest guest = guestRepository.findByUsername(DB_ADMIN_USERNAME);
        assertNull(guest);
    }
}
