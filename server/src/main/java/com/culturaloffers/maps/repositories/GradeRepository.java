package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByCulturalOfferId(int id);
    Page<Grade> findByCulturalOfferId(int id, Pageable pageable);
    List<Grade> findByUserId(int id);
    Page<Grade> findByUserId(int id, Pageable pageable);
}