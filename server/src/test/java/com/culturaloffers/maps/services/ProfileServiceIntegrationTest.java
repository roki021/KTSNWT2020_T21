package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.culturaloffers.maps.constants.ProfileConstatnts.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-profile.properties")
public class ProfileServiceIntegrationTest {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void okTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(USER_ID, USER_NAME);

        assertEquals(USER_ID, guest.getId());
        assertEquals(USER_NAME, guest.getUsername());
    }

    @Test(expected = Exception.class)
    public void unauthorizedTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(USER_ID, DIFFERENT_USER_NAME);

        assertNull(guest);
    }

    @Test(expected = Exception.class)
    public void notFoundTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(FAKE_ID, USER_NAME);

        assertNull(guest);
    }

    @Test
    public void okTestUpdateProfile() throws Exception{
        Guest guest = new Guest();
        guest.setUsername(NEW_USER);
        guest.setFirstName(NEW_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(NEW_EMAIL);
        guest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest updated = profileService.updateProfile(USER_ID,guest, USER_NAME);

        assertEquals(USER_ID, updated.getId());
        assertEquals(NEW_FIRST_NAME, updated.getFirstName());
        assertEquals(NEW_EMAIL, updated.getEmailAddress());
        assertEquals(NEW_USER, updated.getUsername());

        Guest rewindGuest = new Guest();
        rewindGuest.setUsername(USER_NAME);
        rewindGuest.setFirstName(USER_FIRST_NAME);
        rewindGuest.setLastName(USER_LAST_NAME);
        rewindGuest.setEmailAddress(USER_EMAIL);
        rewindGuest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        profileService.updateProfile(USER_ID,rewindGuest, NEW_USER);
    }

    @Test(expected = Exception.class)
    public void unauthorizedTestUpdateProfile() throws Exception{
        Guest guest = new Guest();
        guest.setUsername(NEW_USER);
        guest.setFirstName(NEW_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(NEW_EMAIL);
        guest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest updated = profileService.updateProfile(USER_ID,guest, DIFFERENT_USER_NAME);

        assertNull(updated);
    }

    @Test(expected = Exception.class)
    public void hasEmailTestUpdateProfile() throws Exception{
        Guest guest = new Guest();
        guest.setUsername(USER_NAME);
        guest.setFirstName(NEW_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(DIFFERENT_USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest updated = profileService.updateProfile(USER_ID,guest, USER_NAME);

        assertNull(updated);
    }

    @Test(expected = Exception.class)
    public void hasUserNameTestUpdateProfile() throws Exception{
        Guest guest = new Guest();
        guest.setUsername(DIFFERENT_USER_NAME);
        guest.setFirstName(NEW_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest updated = profileService.updateProfile(USER_ID,guest, USER_NAME);

        assertNull(updated);
    }

    @Test
    public void okTestChangePassword() throws Exception{
        Guest guest = new Guest();
        guest.setId(USER_ID);
        guest.setUsername(USER_NAME);
        guest.setFirstName(USER_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(NEW_USER_PASSWORD));

        profileService.ChangePassword(USER_ID, USER_PASSWORD, NEW_USER_PASSWORD, USER_NAME);

        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertTrue(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));

        profileService.ChangePassword(USER_ID, NEW_USER_PASSWORD, USER_PASSWORD, USER_NAME);
    }

    @Test(expected = Exception.class)
    public void badPasswordTestChangePassword() throws Exception{
        Guest guest = new Guest();
        guest.setId(USER_ID);
        guest.setUsername(USER_NAME);
        guest.setFirstName(USER_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(NEW_USER_PASSWORD));

        profileService.ChangePassword(USER_ID, NEW_USER_PASSWORD, NEW_USER_PASSWORD, USER_NAME);

        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertFalse(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));
    }

    @Test(expected = Exception.class)
    public void unauthorizedTestChangePassword() throws Exception{
        Guest guest = new Guest();
        guest.setId(USER_ID);
        guest.setUsername(USER_NAME);
        guest.setFirstName(USER_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(NEW_USER_PASSWORD));

        profileService.ChangePassword(USER_ID, USER_PASSWORD, NEW_USER_PASSWORD, DIFFERENT_USER_NAME);

        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertFalse(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));
    }

    @Test(expected = Exception.class)
    public void notFoundTestChangePassword() throws Exception{
        Guest guest = new Guest();
        guest.setId(FAKE_ID);
        guest.setUsername(USER_NAME);
        guest.setFirstName(USER_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(NEW_USER_PASSWORD));

        profileService.ChangePassword(FAKE_ID, USER_PASSWORD, NEW_USER_PASSWORD, USER_NAME);

        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertFalse(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));
    }
}
