package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.helper.CommentMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    private CommentMapper commentMapper = new CommentMapper();

    @GetMapping("/culturaloffer/{id}")
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

    @GetMapping("/usercomments/{id}")
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

    @PostMapping
    public CommentDTO addComment(@RequestBody Comment comment)
    {
        return commentMapper.toDto(commentService.addComment(comment));
    }

    @DeleteMapping("/{id}")
    public Map< String, Boolean > deleteComment(@PathVariable(value = "id") Integer commentId)
    {
        return commentService.deleteById(commentId);
    }

    @PutMapping("/{id}")
    public ResponseEntity <CommentDTO> updateComment(@PathVariable(value = "id") Integer commentId, @RequestBody Comment commentDetails)
    {
        return ResponseEntity.ok(commentMapper.toDto(commentService.updateComment(commentId, commentDetails)));
    }
}
