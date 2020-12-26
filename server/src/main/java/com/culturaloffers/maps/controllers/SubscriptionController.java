package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.dto.SubscriptionDTO;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

}
