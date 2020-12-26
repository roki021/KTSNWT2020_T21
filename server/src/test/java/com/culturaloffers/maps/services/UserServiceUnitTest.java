package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        User deletedUser = new Guest(
                NEW_GUEST_FIRST_NAME,
                NEW_GUEST_LAST_NAME,
                NEW_GUEST_EMAIL_ADDRESS,
                NEW_GUEST_USERNAME,
                NEW_GUEST_PASSWORD);
        deletedUser.setId(NEW_GUEST_ID);

        given(userRepository.findById(NEW_GUEST_ID)).willReturn(java.util.Optional.of(deletedUser));

        doNothing().when(userRepository).delete(deletedUser);
    }

    @Test
    public void testDeleteOk() {
        boolean deleted = userService.delete(NEW_GUEST_ID);

        verify(userRepository, times(1)).findById(NEW_GUEST_ID);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteFail() {
        boolean deleted = userService.delete(DB_GUEST_ID);

        verify(userRepository, times(1)).findById(DB_GUEST_ID);

        assertFalse(deleted);
    }

}
