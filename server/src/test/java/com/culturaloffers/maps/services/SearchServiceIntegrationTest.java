package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static com.culturaloffers.maps.constants.SearchConstants.*;
import static com.culturaloffers.maps.constants.SearchConstants.BAD_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-search.properties")
public class SearchServiceIntegrationTest {
    @Autowired
    private CulturalOfferService culturalOfferService;

    @Test
    public void okTestSearchTitle() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(TITLE, SEARCH_TITLE);

        assertEquals(EXPECT_TITLE, culturalOffers.size());
    }

    @Test
    public void notFoundTestSearchTitle() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_TITLE, SEARCH_TITLE);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestSearchDescription() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(DESCRIPTION, SEARCH_DESCRIPTION);

        assertEquals(EXPECT_DESCRIPTION, culturalOffers.size());
    }

    @Test
    public void notFoundTestFindByDescriptionContaining() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_DESCRIPTION, SEARCH_DESCRIPTION);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestSearchSubtype() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(SUBTYPE, SEARCH_SUBTYPE);

        assertEquals(EXPECT_SUBTYPE, culturalOffers.size());
    }

    @Test
    public void notFoundTestSearchSubtype() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_SUBTYPE, SEARCH_SUBTYPE);

        assertEquals(0, culturalOffers.size());
    }

    @Test
    public void okTestSearchSubscribers() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(SUBSCRIBERS, SEARCH_SUBSCRIBERS);

        assertEquals(EXPECT_SUBSCRIBERS, culturalOffers.size());
    }

    @Test
    public void notFoundTestSearchSubscribers() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_SUBSCRIBERS, SEARCH_SUBSCRIBERS);

        assertEquals(0, culturalOffers.size());
    }

    @Test(expected = Exception.class)
    public void invalidTestSearchSubscribers() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(INVALID_SUBSCRIBERS, SEARCH_SUBSCRIBERS);

        assertNull(culturalOffers);
    }

    @Test
    @Transactional
    public void okTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(GRADE, SEARCH_GRADE);

        assertEquals(EXPECT_GRADE, culturalOffers.size());
    }

    @Test
    @Transactional
    public void notFoundTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_GRADE, SEARCH_GRADE);

        assertEquals(0, culturalOffers.size());
    }

    @Test(expected = Exception.class)
    public void invalidTestSearchGrade() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(INVALID_GRADE, SEARCH_GRADE);

        assertNull(culturalOffers);
    }

    @Test
    public void okTestSearchType() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(TYPE, SEARCH_TYPE);

        assertEquals(EXPECT_TYPE, culturalOffers.size());
    }

    @Test
    public void notFoundTestSearchType() throws Exception{
        List<CulturalOffer> culturalOffers = culturalOfferService.search(BAD_TYPE, SEARCH_TYPE);

        assertEquals(0, culturalOffers.size());
    }

}
