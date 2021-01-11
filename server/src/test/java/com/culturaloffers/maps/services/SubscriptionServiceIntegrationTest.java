package com.culturaloffers.maps.services;


import com.culturaloffers.maps.dto.SubscriptionDTO;
import com.culturaloffers.maps.model.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static com.culturaloffers.maps.constants.GradesConstants.ExpectedNotZero;
import static com.culturaloffers.maps.constants.GradesConstants.FindByCulturalIDNotZeroData;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class SubscriptionServiceIntegrationTest {

    @Autowired
    SubscriptionService subscriptionService;

    @Test
    @Transactional
    public void okTestFindByUserIdNotZero(){
        List<SubscriptionDTO> grades = subscriptionService.getAllUserSubscriptions(1001);
        assertEquals(2, grades.size());
    }

    @Test
    @Transactional
    public void okTestFindByCulturalOfferIdNotZero(){
        List<SubscriptionDTO> grades = subscriptionService.getAllCulturalOfferSubscribers(1);
        assertEquals(1, grades.size());
    }

    @Test
    @Transactional
    public void okAddSubscription(){
        subscriptionService.addSubscription(new SubscriptionDTO(1004, 15));

        List<SubscriptionDTO> grades = subscriptionService.getAllCulturalOfferSubscribers(15);
        assertEquals(1, grades.size());
    }

    @Test
    @Transactional
    public void okDeleteSubscription(){
        subscriptionService.deleteSubscription(new SubscriptionDTO(1004, 15));

        List<SubscriptionDTO> grades = subscriptionService.getAllCulturalOfferSubscribers(15);
        assertEquals(0, grades.size());
    }
}