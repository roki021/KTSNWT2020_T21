package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.SubscriptionDTO;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.repositories.CommentRepository;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    CulturalOfferRepository coRepository;

    @Autowired
    GuestRepository guestRepository;

    public void addSubscription(SubscriptionDTO subscriptionDTO)
    {
        List<CulturalOffer> culturalOffers = coRepository.findAll();
        CulturalOffer culturalOffer = coRepository.findById(subscriptionDTO.getCulturalOfferId())
                .orElseThrow(() -> new ResourceNotFoundException("Cultural offer not found for this id :: "
                        + subscriptionDTO.getCulturalOfferId()));

        Guest guest = guestRepository.findById(subscriptionDTO.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: "
                + subscriptionDTO.getGuestId()));

        //guest.getSubscriptions().add(culturalOffer);
        culturalOffer.getSubscribers().add(guest);

        for(Guest g : culturalOffer.getSubscribers())
            System.out.println(g.getId());

        coRepository.save(culturalOffer);
    }

    public Map<String, Boolean> deleteSubscription(SubscriptionDTO subscriptionDTO)
    {
        List<CulturalOffer> culturalOffers = coRepository.findAll();
        CulturalOffer culturalOffer = coRepository.findById(subscriptionDTO.getCulturalOfferId())
                .orElseThrow(() -> new ResourceNotFoundException("Cultural offer not found for this id :: "
                        + subscriptionDTO.getCulturalOfferId()));

        Guest guest = guestRepository.findById(subscriptionDTO.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: "
                        + subscriptionDTO.getGuestId()));

        culturalOffer.getSubscribers().remove(guest);

        coRepository.save(culturalOffer);

        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
