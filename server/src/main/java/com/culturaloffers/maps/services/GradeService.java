package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.repositories.CommentRepository;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import com.culturaloffers.maps.repositories.GradeRepository;
import com.culturaloffers.maps.repositories.GuestRepository;
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
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    CulturalOfferRepository culturalOfferRepository;

    @Autowired
    GuestRepository guestRepository;

    GradeMapper gradeMapper = new GradeMapper();

    public List<Grade> findByCulturalOfferId(int id)
    {
        return gradeRepository.findByCulturalOfferId(id);
    }

    public Page<Grade> findByCulturalOfferId(int id, Pageable pageable)
    {
        return gradeRepository.findByCulturalOfferId(id, pageable);
    }

    public List<Grade> findByUserId(int id)
    {
        return gradeRepository.findByUserId(id);
    }

    public Page<Grade> findByUserId(int id, Pageable pageable)
    {
        return gradeRepository.findByUserId(id, pageable);
    }

    public GradeDTO addGrade(GradeDTO grade)
    {
        Grade gradeToAdd = new Grade();
        gradeToAdd.setValue(grade.getValue());
        gradeToAdd.setGradedOn(grade.getGradedOn());
        CulturalOffer co = culturalOfferRepository.findByTitle(grade.getCulturalOfferName());
        Guest g = guestRepository.findByUsername(grade.getUserUsername());
        gradeToAdd.setCulturalOffer(co);
        gradeToAdd.setUser(g);

        gradeRepository.save(gradeToAdd);
        return gradeMapper.toDto(gradeToAdd);
    }

    public Map<String, Boolean> deleteById(int gradeId) throws ResourceNotFoundException
    {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + gradeId));

        gradeRepository.delete(grade);
        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Grade updateGrade(int gradeId, GradeDTO gradeDetails) throws ResourceNotFoundException {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + gradeId));

        grade.setValue(gradeDetails.getValue());
        grade.setGradedOn(gradeDetails.getGradedOn());

        final Grade updatedGrade = gradeRepository.save(grade);

        return updatedGrade;
    }
}
