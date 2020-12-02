package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findByCulturalOfferId(int id)
    {
        return commentRepository.findByCulturalOfferId(id);
    }

    public Page<Comment> findByCulturalOfferId(int id, Pageable pageable)
    {
        return commentRepository.findByCulturalOfferId(id, pageable);
    }

    public List<Comment> findByUserId(int id)
    {
        return commentRepository.findByUserId(id);
    }

    public Page<Comment> findByUserId(int id, Pageable pageable)
    {
        return commentRepository.findByUserId(id, pageable);
    }

    public Comment addComment(Comment comment)
    {
        return commentRepository.save(comment);
    }

    public Map<String, Boolean> deleteById(int commentId) throws ResourceNotFoundException
    {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + commentId));

        commentRepository.delete(comment);
        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Comment updateComment(int commentId, Comment commentDetails) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + commentId));

        comment.setContent(commentDetails.getContent());
        comment.setImageUrls(commentDetails.getImageUrls());
        comment.setCommentedOn(commentDetails.getCommentedOn());

        final Comment updatedComment = commentRepository.save(comment);

        return updatedComment;
    }
}
