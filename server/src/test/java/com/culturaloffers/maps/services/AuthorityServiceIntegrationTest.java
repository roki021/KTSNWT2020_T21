package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Authority;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class AuthorityServiceIntegrationTest {

    @Autowired
    private AuthorityService authorityService;

    @Test
    public void testFindById() {
        List<Authority> authorities = authorityService.findById(USER_ROLE_ID);
        assertEquals(USER_ROLE_ID, authorities.get(0).getId());
    }

    @Test(expected = LazyInitializationException.class)
    public void testFindByIdBad() {
        List<Authority> authorities = authorityService.findById(USER_ROLE_ID_BAD);
        assertNull(authorities.get(0).getName());
    }

    @Test
    public void testFindByName() {
        List<Authority> authorities = authorityService.findByName(USER_ROLE);
        assertEquals(USER_ROLE, authorities.get(0).getName());
    }

    @Test
    public void testFindByNameBad() {
        List<Authority> authorities = authorityService.findByName(USER_ROLE_BAD);
        assertNull(authorities.get(0));
    }
}
