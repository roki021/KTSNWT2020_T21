package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.CommentMapper;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static com.culturaloffers.maps.constants.GradesConstants.*;
import static com.culturaloffers.maps.constants.GradesConstants.FindByUserIDZeroData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class CommentsServiceIntegrationTest {
    @Autowired
    CommentService commentService;

    CommentMapper commentMapper = new CommentMapper();

    @Test
    public void okTestFindByCulturalOfferIdNotZero(){
        List<Comment> grades = commentService.findByCulturalOfferId(FindByCulturalIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByCulturalOfferIdZero(){
        List<Comment> grades = commentService.findByCulturalOfferId(FindByCulturalIDZeroData);
        assertEquals(0, grades.size());
    }

    @Test
    public void okTestFindByUserIdNotZero(){
        List<Comment> grades = commentService.findByUserId(FindByUserIDNotZeroData);
        assertEquals(ExpectedNotZero, grades.size());
    }

    @Test
    public void okTestFindByUserIdZero(){
        List<Comment> grades = commentService.findByUserId(FindByUserIDZeroData);
        assertEquals(0, grades.size());
    }

    @Test
    public void okTestCreate() throws Exception{
        CommentDTO commentDTO = new CommentDTO(
                888,
                "Komentar",
                new Date(),
                new ArrayList<String>(),
                1001,
                14,
                "perica",
                "Cvetni Konaci"
        );

        CommentDTO created = commentService.addComment(commentDTO);

        assertEquals("Komentar", created.getContent());
    }

    @Test
    public void failTestCreate() throws Exception{
        assertThrows(ConstraintViolationException.class, () -> {
            CommentDTO commentDTO = new CommentDTO(
                    888,
                    "",
                    new Date(),
                    new ArrayList<String>(),
                    1001,
                    14,
                    "perica",
                    "Cvetni Konaci"
            );

            CommentDTO created = commentService.addComment(commentDTO);

            assertEquals("Komentar", created.getContent());
        });
    }

    @Test
    public void okTestDelete() throws Exception{
        Map<String, Boolean> expected = new HashMap<String, Boolean>();
        expected.put("deleted", Boolean.TRUE);

        Map<String, Boolean> actual = commentService.deleteById(5);

        assertEquals(expected, actual);
    }

    @Test
    public void failTestDelete() throws Exception{
        assertThrows(ResourceNotFoundException.class, () -> {
            commentService.deleteById(156);
        });
    }

    @Test
    @Transactional
    public void okTestUpdate() throws Exception{
        List<Comment> comments = commentService.findByUserId(1005);
        System.out.println(comments.size());
        Comment toBeChanged = comments.get(0);
        toBeChanged.setContent("Izmenjen content");

        CommentDTO dto = commentMapper.toDto(toBeChanged);

        Comment changed = commentService.updateComment(toBeChanged.getId(), dto);

        assertEquals("Izmenjen content", changed.getContent());
    }

    @Test
    public void failTestUpdateNotFound() throws Exception{
        assertThrows(ResourceNotFoundException.class, () -> {
            commentService.updateComment(156, new CommentDTO());
        });
    }
}
