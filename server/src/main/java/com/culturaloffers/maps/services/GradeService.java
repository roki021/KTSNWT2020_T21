package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.repositories.CommentRepository;
import com.culturaloffers.maps.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<GradeDTO> findByCulturalOfferId(int id)
    {
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        for (Grade g: gradeRepository.findByCulturalOfferId(id)) {
            gradeDTOS.add(new GradeDTO(g));
        }
        return gradeDTOS;
    }

    public List<GradeDTO> findByUserId(int id)
    {
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        for (Grade g: gradeRepository.findByUserId(id)) {
            gradeDTOS.add(new GradeDTO(g));
        }
        return gradeDTOS;
    }

    public Grade addGrade(Grade grade)
    {
        return gradeRepository.save(grade);
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

    public GradeDTO updateGrade(int gradeId, Grade gradeDetails) throws ResourceNotFoundException {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + gradeId));

        grade.setValue(gradeDetails.getValue());
        grade.setGradedOn(gradeDetails.getGradedOn());

        final Grade updatedGrade = gradeRepository.save(grade);
        GradeDTO updatedGradeDTO = new GradeDTO(updatedGrade);

        return updatedGradeDTO;
    }
}
