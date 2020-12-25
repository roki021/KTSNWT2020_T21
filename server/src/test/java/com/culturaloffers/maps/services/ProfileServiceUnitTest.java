package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.culturaloffers.maps.constants.ProfileConstatnts.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-profile.properties")
public class ProfileServiceUnitTest {

    @MockBean
    private GuestRepository guestRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup(){
        Guest guest = new Guest();
        guest.setUsername(USER_NAME);
        guest.setFirstName(USER_FIRST_NAME);
        guest.setLastName(USER_LAST_NAME);
        guest.setEmailAddress(USER_EMAIL);
        guest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest savedGuest = new Guest();
        savedGuest.setId(USER_ID);
        savedGuest.setUsername(USER_NAME);
        savedGuest.setFirstName(USER_FIRST_NAME);
        savedGuest.setLastName(USER_LAST_NAME);
        savedGuest.setEmailAddress(USER_EMAIL);
        savedGuest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        Guest differentGuest = new Guest();
        differentGuest.setId(DIFFERENT_USER_ID);
        differentGuest.setUsername(DIFFERENT_USER_NAME);
        differentGuest.setFirstName(DIFFERENT_USER_FIRST_NAME);
        differentGuest.setLastName(USER_LAST_NAME);
        differentGuest.setEmailAddress(DIFFERENT_USER_EMAIL);
        differentGuest.setPassword(passwordEncoder.encode(USER_PASSWORD));


        given(guestRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        given(guestRepository.findById(USER_ID)).willReturn(Optional.of(savedGuest));

        given(userRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(savedGuest));

        given(guestRepository.findByEmailAddress(USER_EMAIL)).willReturn(savedGuest);
        given(guestRepository.findByUsername(USER_NAME)).willReturn(savedGuest);

        given(guestRepository.findByEmailAddress(DIFFERENT_USER_EMAIL)).willReturn(differentGuest);
        given(guestRepository.findByUsername(DIFFERENT_USER_NAME)).willReturn(differentGuest);

        given(guestRepository.findByEmailAddress(NEW_EMAIL)).willReturn(null);
        given(guestRepository.findByUsername(NEW_USER)).willReturn(null);

        Guest updatedGuest = new Guest();
        updatedGuest.setId(USER_ID);
        updatedGuest.setUsername(NEW_USER);
        updatedGuest.setFirstName(NEW_FIRST_NAME);
        updatedGuest.setLastName(USER_LAST_NAME);
        updatedGuest.setEmailAddress(NEW_EMAIL);
        updatedGuest.setPassword(passwordEncoder.encode(USER_PASSWORD));

        given(guestRepository.save(updatedGuest)).willReturn(updatedGuest);
        given(userRepository.save(updatedGuest)).willReturn(updatedGuest);
    }

    @Test
    public void okTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(USER_ID, USER_NAME);

        verify(guestRepository,times(1)).findById(USER_ID);

        assertEquals(USER_ID, guest.getId());
    }

    @Test(expected = Exception.class)
    public void unauthorizedTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(DIFFERENT_USER_ID, USER_NAME);

        verify(guestRepository,times(1)).findById(DIFFERENT_USER_ID);

        assertNull(guest);
    }

    @Test(expected = Exception.class)
    public void notFoundTestFindProfile() throws Exception{
        Guest guest = profileService.findProfile(FAKE_ID, USER_NAME);

        verify(guestRepository,times(1)).findById(FAKE_ID);

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

        verify(guestRepository,times(1)).findById(USER_ID);
        verify(guestRepository,times(1)).findByEmailAddress(NEW_EMAIL);
        verify(guestRepository,times(1)).findByUsername(NEW_USER);
        verify(guestRepository,times(1)).save(guest);

        assertEquals(USER_ID, updated.getId());
        assertEquals(NEW_FIRST_NAME, updated.getFirstName());
        assertEquals(NEW_EMAIL, updated.getEmailAddress());
        assertEquals(NEW_USER, updated.getUsername());
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

        verify(guestRepository,times(1)).findById(USER_ID);
        verify(guestRepository,times(0)).findByEmailAddress(NEW_EMAIL);
        verify(guestRepository,times(0)).findByUsername(NEW_USER);
        verify(guestRepository,times(0)).save(guest);

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

        verify(guestRepository,times(1)).findById(DIFFERENT_USER_ID);
        verify(guestRepository,times(1)).findByEmailAddress(NEW_EMAIL);
        verify(guestRepository,times(0)).findByUsername(NEW_USER);
        verify(guestRepository,times(0)).save(guest);

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

        verify(guestRepository,times(1)).findById(DIFFERENT_USER_ID);
        verify(guestRepository,times(1)).findByEmailAddress(NEW_EMAIL);
        verify(guestRepository,times(1)).findByUsername(NEW_USER);
        verify(guestRepository,times(0)).save(guest);

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

        verify(userRepository,times(1)).findById(USER_ID);
        verify(userRepository,times(1)).save(guest);

        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertTrue(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));
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

        verify(userRepository,times(1)).findById(USER_ID);
        verify(userRepository,times(0)).save(guest);

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

        verify(userRepository,times(1)).findById(USER_ID);
        verify(userRepository,times(0)).save(guest);
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

        verify(userRepository,times(1)).findById(FAKE_ID);
        verify(userRepository,times(0)).save(guest);
        Guest updated = profileService.findProfile(USER_ID, USER_NAME);

        assertFalse(passwordEncoder.matches(NEW_USER_PASSWORD, updated.getPassword()));
    }


}
