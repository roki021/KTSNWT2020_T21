package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByCulturalOfferId(int id);
    List<Comment> findByUserId(int id);
}
