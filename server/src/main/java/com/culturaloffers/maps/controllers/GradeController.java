package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.services.CommentService;
import com.culturaloffers.maps.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/g")
public class GradeController {
    @Autowired
    GradeService gradeService;

    private GradeMapper gradeMapper = new GradeMapper();

    @GetMapping("/culturaloffer/grades/{id}")
    public ResponseEntity<List<GradeDTO>> findByCulturalOfferId(@PathVariable int id)
    {
        try {
            List<GradeDTO> offerGrades = gradeMapper.toDtoList(gradeService.findByCulturalOfferId(id));

            if (offerGrades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(offerGrades, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/culturaloffer/averagegrade/{offerId}")
    public double averageGradeOfCulturalOfferId(@PathVariable int offerId)
    {
        return gradeService.averageGradeOfCulturalOffer(offerId);
    }

    @GetMapping("/culturaloffer/specific/{offerId}/{userId}")
    public int specificGrade(@PathVariable int offerId, @PathVariable int userId)
    {
        return gradeService.specificGrade(userId, offerId);
    }

    @GetMapping("/culturalofferpg/grades/{id}")
    public Page<GradeDTO> findByCulturalOfferIdPageable(@PathVariable int id, Pageable pageable)
    {
        Page<Grade> grades = gradeService.findByCulturalOfferId(id, pageable);
        return new PageImpl<GradeDTO>(
                gradeMapper.toDtoList(grades.getContent()), pageable, grades.getTotalElements()
        );
    }

    @GetMapping("/user/grades/{id}")
    public ResponseEntity<List<GradeDTO>> findByUserId(@PathVariable int id)
    {
        try {
            List<GradeDTO> userGrades = gradeMapper.toDtoList(gradeService.findByUserId(id));

            if (userGrades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userGrades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/gradespg/{id}")
    public Page<GradeDTO> findByUserIdPageable(@PathVariable int id, Pageable pageable)
    {
        Page<Grade> grades = gradeService.findByCulturalOfferId(id, pageable);
        return new PageImpl<GradeDTO>(
                gradeMapper.toDtoList(grades.getContent()), pageable, grades.getTotalElements()
        );
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping
    public GradeDTO addGrade(@Valid @RequestBody GradeDTO gradeDTO)
    {
        Grade grade = gradeMapper.toEntity(gradeDTO);
        return gradeMapper.toDto(gradeService.addGrade(grade, gradeDTO.getCulturalOfferId(), gradeDTO.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @DeleteMapping("grade/{id}")
    public Map< String, Boolean > deleteGrade(@PathVariable(value = "id") Integer gradeId)
    {
        return gradeService.deleteById(gradeId);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PutMapping("grade/{id}")
    public ResponseEntity <GradeDTO> updateGrade(@PathVariable(value = "id") Integer gradeId, @Valid @RequestBody GradeDTO gradeDetails)
    {
        Grade grade = gradeMapper.toEntity(gradeDetails);
        return ResponseEntity.ok(gradeMapper.toDto(gradeService.updateGrade(gradeId, grade)));
    }
}
