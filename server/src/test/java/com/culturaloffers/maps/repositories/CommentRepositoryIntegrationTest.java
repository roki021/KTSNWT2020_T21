package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.culturaloffers.maps.constants.GradesConstants.*;
import static com.culturaloffers.maps.constants.GradesConstants.FindByUserIDZeroData;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class CommentRepositoryIntegrationTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void okTestFindByCulturalOfferIdNotZero(){
        List<Comment> comments = commentRepository.findByCulturalOfferId(FindByCulturalIDNotZeroData);
        assertEquals(ExpectedNotZero, comments.size());
    }

    @Test
    public void okTestFindByCulturalOfferIdZero(){
        List<Comment> comments = commentRepository.findByCulturalOfferId(FindByCulturalIDZeroData);
        assertEquals(0, comments.size());
    }

    @Test
    public void okTestFindByUserIdNotZero(){
        List<Comment> comments = commentRepository.findByUserId(FindByUserIDNotZeroData);
        assertEquals(ExpectedNotZero, comments.size());
    }

    @Test
    public void okTestFindByUserIdZero(){
        List<Comment> comments = commentRepository.findByUserId(FindByUserIDZeroData);
        assertEquals(0, comments.size());
    }
}
