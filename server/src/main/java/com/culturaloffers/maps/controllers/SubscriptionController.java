package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.GradeDTO;
import com.culturaloffers.maps.dto.SubscriptionDTO;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.Grade;
import com.culturaloffers.maps.services.SubscriptionService;
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
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService = new SubscriptionService();

    @PostMapping
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public SubscriptionDTO addSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO)
    {
        subscriptionService.addSubscription(subscriptionDTO);
        return subscriptionDTO;
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public Map< String, Boolean > deleteSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO)
    {
        return subscriptionService.deleteSubscription(subscriptionDTO);
    }

    @GetMapping("/culturaloffer/{id}")
    public ResponseEntity<List<SubscriptionDTO>> findByCulturalOfferId(@PathVariable int id)
    {
        List<SubscriptionDTO> subs = subscriptionService.getAllCulturalOfferSubscribers(id);
        return new ResponseEntity<>(subs, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<SubscriptionDTO>> findByUserId(@PathVariable int id)
    {
        List<SubscriptionDTO> subs = subscriptionService.getAllUserSubscriptions(id);
        return new ResponseEntity<>(subs, HttpStatus.OK);
    }

}
