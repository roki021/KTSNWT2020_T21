package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.model.Subtype;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.culturaloffers.maps.constants.SearchConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-search.properties")
public class SearchServiceUnitTest {
    @MockBean
    private CulturalOfferRepository culturalOfferRepository;

    @Autowired
    private CulturalOfferService culturalOfferService;

    @Before
    public void setup(){
        CulturalOffer culturalOffer = new CulturalOffer();
        culturalOffer.setId(ID);
        culturalOffer.setUserGrades(new HashSet<Grade>());

        Grade grade = new Grade();
        grade.setValue(HELP_GRADE);
        culturalOffer.getUserGrades().add(grade);
        List<CulturalOffer> culturalOfferList = new ArrayList<CulturalOffer>();
        culturalOfferList.add(culturalOffer);

        given(culturalOfferRepository.findAll()).willReturn(culturalOfferList);
        given(culturalOfferRepository.findGraded()).willReturn(culturalOfferList);
    }

    @Test
    @Transactional
    public void okTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(GRADE, SEARCH_GRADE);
        verify(culturalOfferRepository, times(1)).findGraded();

        assertEquals(1, culturalOffers.size());
    }

    @Test(expected = Exception.class)
    public void negativeGradeTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(NEGATIVE_GRADE, SEARCH_GRADE);
        verify(culturalOfferRepository, times(0)).findGraded();

        assertNull(culturalOffers);
    }

    @Test(expected = Exception.class)
    public void invalidGradeTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(INVALID_GRADE, SEARCH_GRADE);
        verify(culturalOfferRepository, times(0)).findGraded();

        assertNull(culturalOffers);
    }
}
