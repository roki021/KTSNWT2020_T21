package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Admin;
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
public class AdminRepositoryIntegrationTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void findByUsername() {
        Admin admin = adminRepository.findByUsername(DB_ADMIN_USERNAME);
        assertEquals(DB_ADMIN_USERNAME, admin.getUsername());
    }

    @Test
    public void findAll() {
        List<Admin> admins = adminRepository.findAll(PageRequest.of(DB_USER_PAGE, DB_USER_SIZE)).getContent();
        assertEquals(DB_ADMIN_EXPECTED, admins.size());
    }

    @Test
    public void testFindByUsernameGuest() {
        Admin admin = adminRepository.findByUsername(DB_GUEST_USERNAME);
        assertNull(admin);
    }
}
