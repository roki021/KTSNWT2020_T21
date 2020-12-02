package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.helper.GradeMapper;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.services.CommentService;
import com.culturaloffers.maps.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    GradeService gradeService;

    private GradeMapper gradeMapper = new GradeMapper();

    @GetMapping("/culturaloffer/{id}")
    public ResponseEntity<List<GradeDTO>> findByCulturalOfferId(@PathVariable int id)
    {
        try {
            List<GradeDTO> offerGrades = gradeMapper.toDtoList(gradeService.findByCulturalOfferId(id));

            if (offerGrades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(offerGrades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usergrades/{id}")
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

    @PostMapping
    public GradeDTO addGrade(@RequestBody Grade grade)
    {
        return gradeMapper.toDto(gradeService.addGrade(grade));
    }

    @DeleteMapping("/{id}")
    public Map< String, Boolean > deleteGrade(@PathVariable(value = "id") Integer gradeId)
    {
        return gradeService.deleteById(gradeId);
    }

    @PutMapping("/{id}")
    public ResponseEntity <GradeDTO> updateGrade(@PathVariable(value = "id") Integer gradeId, @RequestBody Grade gradeDetails)
    {
        return ResponseEntity.ok(gradeMapper.toDto(gradeService.updateGrade(gradeId, gradeDetails)));
    }
}
