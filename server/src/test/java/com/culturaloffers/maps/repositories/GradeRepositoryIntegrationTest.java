package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.model.OfferType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.OfferTypeConstants.DB_OFFER_TYPE;
import static org.junit.Assert.assertEquals;
import static com.culturaloffers.maps.constants.GradesConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class GradeRepositoryIntegrationTest {

    @Autowired
    GradeRepository gradeRepository;

    @Test
    public void okTestFindByCulturalOfferIdNotZero(){
        List<Grade> grades = gradeRepository.findByCulturalOfferId(FindByCulturalIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByCulturalOfferIdZero(){
        List<Grade> grades = gradeRepository.findByCulturalOfferId(FindByCulturalIDZeroData);
        assertEquals(0, grades.size());
    }

    @Test
    public void okTestFindByUserIdNotZero(){
        List<Grade> grades = gradeRepository.findByUserId(FindByUserIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByUserIdZero(){
        List<Grade> grades = gradeRepository.findByUserId(FindByUserIDZeroData);
        assertEquals(0, grades.size());
    }
}
