package com.culturaloffers.maps.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import static com.culturaloffers.maps.constants.OfferNewsConstants.*;

import com.culturaloffers.maps.model.OfferNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-2.properties")
public class OfferNewsRepositoryTest {

    @Autowired
    private OfferNewsRepository repository;

    @Test
    public void testFindAll(){
        List<OfferNews> all = repository.findAll();
        assertThat(all.size()).isEqualTo(5);
    }

}
