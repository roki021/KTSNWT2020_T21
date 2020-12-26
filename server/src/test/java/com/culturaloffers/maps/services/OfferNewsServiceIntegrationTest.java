package com.culturaloffers.maps.services;

import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_ID;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_DATE;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_DESCRIPTION;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_NEW_DESCRIPTION;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_NEW_TITLE;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_OFFER;
import static com.culturaloffers.maps.constants.OfferNewsConstants.ON_TITLE;

import com.culturaloffers.maps.model.OfferNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-offer.properties")
public class OfferNewsServiceIntegrationTest {

    @Autowired
    private OfferNewsService service;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testFindAll(){
        List<OfferNews> all = service.findAll();
        assertThat(all.size()).isEqualTo(5);
    }

    @Test
    public void testFindSomePageable(){
        PageRequest request = PageRequest.of(1, 2);
        Page<OfferNews> page = service.findAll(request);
        assertThat(page).hasSize(2);
    }

    @Test
    public void testFindOne(){
        OfferNews news = service.findOne(ON_ID);
        assertThat(news).isNotNull();
        assertThat(news.getId()).isEqualTo(ON_ID);
        assertThat(news.getTitle()).isEqualTo(ON_TITLE);
        assertThat(news.getDescription()).isEqualTo(ON_DESCRIPTION);
        assertThat(sdf.format(news.getDate())).isEqualTo(ON_DATE);
        assertThat(news.getCulturalOffer().getId()).isEqualTo(ON_OFFER);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws Exception {
        ArrayList<String> imgs = new ArrayList<>();
        OfferNews news = new OfferNews(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs);
        int sizeBefore = service.findAll().size();
        news = service.create(news, ON_OFFER);
        assertThat(service.findAll().size()).isGreaterThan(sizeBefore);
        Integer id = news.getId();
        assertThat(service.findOne(id)).isNotNull();
        assertThat(service.findOne(id).getId()).isNotNull();
        assertThat(service.findOne(id).getDate()).isNotNull();
        assertThat(service.findOne(id).getDescription()).isEqualTo(ON_NEW_DESCRIPTION);
        assertThat(service.findOne(id).getTitle()).isEqualTo(ON_NEW_TITLE);
        assertThat(service.findOne(id).getCulturalOffer().getId()).isEqualTo(ON_OFFER);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testCreateWithInvalidCulturalOffer() throws Exception {
        ArrayList<String> imgs = new ArrayList<>();
        OfferNews news = new OfferNews(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs);
        service.create(news, 50);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() throws Exception {
        ArrayList<String> imgs = new ArrayList<>();
        OfferNews news = new OfferNews(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs);
        int sizeBefore = service.findAll().size();
        news = service.update(ON_ID, news);
        assertThat(service.findAll().size()).isEqualTo(sizeBefore);
        Integer id = news.getId();
        assertThat(service.findOne(id)).isNotNull();
        assertThat(service.findOne(id).getId()).isEqualTo(ON_ID);
        assertThat(service.findOne(id).getDate()).isAfter(sdf.parse(ON_DATE));
        assertThat(service.findOne(id).getDescription()).isEqualTo(ON_NEW_DESCRIPTION);
        assertThat(service.findOne(id).getTitle()).isEqualTo(ON_NEW_TITLE);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testUpdateWithInvalidId() throws Exception {
        ArrayList<String> imgs = new ArrayList<>();
        OfferNews news = new OfferNews(null, ON_NEW_TITLE, ON_NEW_DESCRIPTION, imgs);
        news = service.update(50, news);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() throws Exception {
        int sizeBefore = service.findAll().size();
        assertThat(service.findOne(ON_ID)).isNotNull();
        service.delete(ON_ID);
        assertThat(service.findOne(ON_ID)).isNull();
        assertThat(service.findAll().size()).isLessThan(sizeBefore);
    }

    @Test(expected = Exception.class)
    @Transactional
    @Rollback(true)
    public void testDeleteWithInvalidId() throws Exception {
        service.delete(40);
    }

}
