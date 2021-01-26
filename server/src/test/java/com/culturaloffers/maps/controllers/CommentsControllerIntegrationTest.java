package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.dto.UserLoginDTO;
import com.culturaloffers.maps.dto.UserTokenStateDTO;
import com.culturaloffers.maps.helper.CommentMapper;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.services.CommentService;
import com.culturaloffers.maps.services.CulturalOfferService;
import com.culturaloffers.maps.services.GradeService;
import com.culturaloffers.maps.services.GuestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-grades.properties")
public class CommentsControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CulturalOfferService culturalOfferService;

    @Autowired
    private GuestService guestService;

    CommentMapper commentMapper = new CommentMapper();

    private GradeMapper gradeMapper = new GradeMapper();

    private String accessToken;

    private HttpHeaders httpHeaders;

    private void login(String username, String password) throws NullPointerException {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
                new UserLoginDTO(username,password), UserTokenStateDTO.class);
        accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
    }

    @Before
    public void setUp() {
        login("perica", "12345");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessToken);
    }

    @Test
    public void testGetGradesByCulturalOfferId() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<CommentDTO>> responseEntity =
                restTemplate.exchange("/c/culturaloffer/comments/" + 7, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<CommentDTO>>() {});

        List<CommentDTO> comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, comments.size());
    }

    @Test
    public void testGetGradesByUserId() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<List<CommentDTO>> responseEntity =
                restTemplate.exchange("/c/user/comments/" + 1005, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<CommentDTO>>() {});

        List<CommentDTO> comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, comments.size());
    }

    @Test
    public void testAddNewGrade() {
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

        HttpEntity<CommentDTO> httpEntity = new HttpEntity<CommentDTO>(commentDTO, httpHeaders);
        int size = commentService.findByCulturalOfferId(14).size();

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/c",
                        HttpMethod.POST, httpEntity, CommentDTO.class);

        CommentDTO comment = responseEntity.getBody();
        assertNotNull(comment);
        assertEquals("Komentar", comment.getContent());

        List<Comment> comments = commentService.findByCulturalOfferId(14);
        assertEquals(size + 1, comments.size());

        commentService.deleteById(comment.getId());
    }

    @Test
    public void testDeleteGrade() {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
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

        Comment comment = commentService.addComment(commentMapper.toEntity(commentDTO),
                commentDTO.getCulturalOfferId(), commentDTO.getUserId());

        int size = commentService.findByCulturalOfferId(14).size();

        ResponseEntity<Map<String, Boolean>> responseEntity =
                restTemplate.exchange("/c/" + comment.getId(),
                        HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<Map<String, Boolean>>(){});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, commentService.findByCulturalOfferId(14).size());
    }

    @Test
    @Transactional
    public void testUpdateGrade() {
        List<Comment> comments = commentService.findByUserId(1001);
        Comment toBeChanged = comments.get(0);

        toBeChanged.setContent("changed");
        CommentDTO dto = commentMapper.toDto(toBeChanged);
        HttpEntity<CommentDTO> httpEntity = new HttpEntity<CommentDTO>(dto, httpHeaders);
        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/c/" + toBeChanged.getId(), HttpMethod.PUT, httpEntity, CommentDTO.class);

        CommentDTO commentChanged = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(commentChanged);
        assertEquals(new Integer(1), commentChanged.getId());
        assertEquals("changed", commentChanged.getContent());


        commentChanged.setContent("Prelep pogled sa vrha");
        responseEntity =
                restTemplate.exchange("/c/" + 1, HttpMethod.PUT, httpEntity, CommentDTO.class);
    }
}
