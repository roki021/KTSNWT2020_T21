package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.CommentMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/c")
public class CommentController {
    @Autowired
    CommentService commentService;

    private CommentMapper commentMapper = new CommentMapper();

    @GetMapping("/culturaloffer/comments/{id}")
    public ResponseEntity<List<CommentDTO>> findByCulturalOfferId(@PathVariable int id)
    {
        try {
            List<Comment> offerComments = commentService.findByCulturalOfferId(id);

            if (offerComments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(commentMapper.toDtoList(offerComments), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/culturalofferpg/comments/{id}")
    public Page<CommentDTO> findByCulturalOfferIdPageable(@PathVariable int id, Pageable pageable)
    {
        Page<Comment> comments = commentService.findByCulturalOfferId(id, pageable);
        return new PageImpl<CommentDTO>(
                commentMapper.toDtoList(comments.getContent()), pageable, comments.getTotalElements()
        );
    }

    @GetMapping("/user/comments/{id}")
    public ResponseEntity<List<CommentDTO>> findByUserId(@PathVariable int id)
    {
        try {
            List<Comment> userComments = commentService.findByUserId(id);

            if (userComments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(commentMapper.toDtoList(userComments), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/commentspg/{id}")
    public Page<CommentDTO> findByUserIdPageable(@PathVariable int id, Pageable pageable)
    {
        Page<Comment> comments = commentService.findByCulturalOfferId(id, pageable);
        return new PageImpl<CommentDTO>(
                commentMapper.toDtoList(comments.getContent()), pageable, comments.getTotalElements()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public Map< String, Boolean > addComment(@Valid @RequestBody CommentDTO comment)
    {
        Comment baseComment = commentMapper.toEntity(comment);
        return commentService.addComment(baseComment, comment.getCulturalOfferId(), comment.getUserId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public Map< String, Boolean > deleteComment(@Valid @PathVariable(value = "id") Integer commentId)
    {
        return commentService.deleteById(commentId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public ResponseEntity <CommentDTO> updateComment(@PathVariable(value = "id") Integer commentId, @Valid @RequestBody CommentDTO commentDetails)
    {
        Comment comment = commentMapper.toEntity(commentDetails);
        return ResponseEntity.ok(commentMapper.toDto(commentService.updateComment(commentId, comment)));
    }
}
