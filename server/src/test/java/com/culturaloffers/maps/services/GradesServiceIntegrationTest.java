package com.culturaloffers.maps.services;


import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.model.Subtype;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.culturaloffers.maps.constants.GradesConstants.*;
import static com.culturaloffers.maps.constants.GradesConstants.FindByUserIDZeroData;
import static com.culturaloffers.maps.constants.SubtypeConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class GradesServiceIntegrationTest {
    @Autowired
    GradeService gradeService;

    GradeMapper gradeMapper = new GradeMapper();

    @Test
    public void okTestFindByCulturalOfferIdNotZero(){
        List<Grade> grades = gradeService.findByCulturalOfferId(FindByCulturalIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByCulturalOfferIdZero(){
        List<Grade> grades = gradeService.findByCulturalOfferId(FindByCulturalIDZeroData);
        assertEquals(0, grades.size());
    }

    @Test
    public void okTestFindByUserIdNotZero(){
        List<Grade> grades = gradeService.findByUserId(FindByUserIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByUserIdZero(){
        List<Grade> grades = gradeService.findByUserId(FindByUserIDZeroData);
        assertEquals(0, grades.size());
    }

    @Test
    @Transactional
    public void okTestCreate() throws Exception{
        GradeDTO gradeDTO = new GradeDTO(
                888,
                2,
                new Date(),
                14,
                "Cvetni Konaci",
                1002,
                "mikica"
        );

        Grade created = gradeService.addGrade(gradeMapper.toEntity(gradeDTO), gradeDTO.getCulturalOfferId(), gradeDTO.getUserId());

        assertEquals(2, created.getValue());

        gradeService.deleteById(created.getId());
    }

    @Test
    @Transactional
    public void failTestCreate() throws Exception{
        assertThrows(ConstraintViolationException.class, () -> {
            GradeDTO gradeDTO = new GradeDTO(
                    888,
                    -6,
                    new Date(),
                    14,
                    "Cvetni Konaci",
                    1005,
                    "goPet"
            );

            Grade created = gradeService.addGrade(gradeMapper.toEntity(gradeDTO), gradeDTO.getCulturalOfferId(), gradeDTO.getUserId());
        });
    }

    @Test
    public void okTestDelete() throws Exception{
        Map<String, Boolean> expected = new HashMap<String, Boolean>();
        expected.put("deleted", Boolean.TRUE);

        Map<String, Boolean> actual = gradeService.deleteById(4);

        assertEquals(expected, actual);
    }

    @Test
    public void failTestDelete() throws Exception{
        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.deleteById(156);
        });
    }

    @Test
    public void okTestUpdate() throws Exception{
        List<Grade> grade = gradeService.findByUserId(1001);
        Grade toBeChanged = grade.get(0);
        toBeChanged.setValue(2);


        Grade changed = gradeService.updateGrade(1, toBeChanged);

        assertEquals(2, changed.getValue());
    }

    @Test
    public void failTestUpdateNotFound() throws Exception{
        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.updateGrade(156, new Grade());
        });
    }


}
