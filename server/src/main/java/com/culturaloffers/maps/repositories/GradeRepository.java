package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByCulturalOfferId(int id);
    List<Grade> findByUserId(int id);
}