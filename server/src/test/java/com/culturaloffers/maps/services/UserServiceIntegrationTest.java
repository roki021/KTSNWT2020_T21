package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.User;
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
@TestPropertySource("classpath:test-1.properties")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUsername() {
        User user = userService.getUserByUsername(DB_GUEST_USERNAME);
        assertEquals(DB_GUEST_USERNAME, user.getUsername());
    }

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(DB_GUEST_ID);
        assertEquals(DB_GUEST_ID, user.getId());
    }

    @Test
    public void testGetUsers() {
        List<User> users = userService.getUsers(PageRequest.of(DB_USER_PAGE, DB_USER_SIZE)).getContent();
        assertEquals(DB_USER_EXPECTED, users.size());
    }

    @Test
    @Transactional
    public void testDeleteOkGuest() {
        boolean deleted = userService.delete(DB_GUEST_ID);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    public void testDeleteOkAdmin() {
        boolean deleted = userService.delete(DB_ADMIN_ID);
        assertTrue(deleted);
    }

    @Test
    @Transactional
    public void testDeleteFail() {
        boolean deleted = userService.delete(NEW_GUEST_ID);
        assertFalse(deleted);
    }
}
