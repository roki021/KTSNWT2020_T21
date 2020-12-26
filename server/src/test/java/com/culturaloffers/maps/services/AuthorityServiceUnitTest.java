package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Authority;
import com.culturaloffers.maps.repositories.AuthorityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.UserConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-user-geo.properties")
public class AuthorityServiceUnitTest {

    @Autowired
    private AuthorityService authorityService;

    @MockBean
    private AuthorityRepository authorityRepository;

    @Before
    public void setup() {
        Authority authority = new Authority(
                USER_ROLE
        );
        authority.setId(USER_ROLE_ID);

        given(authorityRepository.getOne(USER_ROLE_ID)).willReturn(authority);
        given(authorityRepository.findByName(USER_ROLE)).willReturn(authority);

        given(authorityRepository.getOne(USER_ROLE_ID_BAD)).willReturn(null);
        given(authorityRepository.findByName(USER_ROLE_BAD)).willReturn(null);
    }

    @Test
    public void testFindById() {
        List<Authority> authorities = authorityService.findById(USER_ROLE_ID);

        verify(authorityRepository, times(1)).getOne(USER_ROLE_ID);

        assertEquals(USER_ROLE_ID, authorities.get(0).getId());
    }

    @Test
    public void testFindByIdBad() {
        List<Authority> authorities = authorityService.findById(USER_ROLE_ID_BAD);

        verify(authorityRepository, times(1)).getOne(USER_ROLE_ID_BAD);

        assertNull(authorities.get(0));
    }

    @Test
    public void testFindByName() {
        List<Authority> authorities = authorityService.findByName(USER_ROLE);

        verify(authorityRepository, times(1)).findByName(USER_ROLE);

        assertEquals(USER_ROLE, authorities.get(0).getName());
    }

    @Test
    public void testFindByNameBad() {
        List<Authority> authorities = authorityService.findByName(USER_ROLE_BAD);

        verify(authorityRepository, times(1)).findByName(USER_ROLE_BAD);

        assertNull(authorities.get(0));
    }
}
