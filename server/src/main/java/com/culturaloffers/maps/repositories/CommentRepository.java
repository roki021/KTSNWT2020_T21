package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
