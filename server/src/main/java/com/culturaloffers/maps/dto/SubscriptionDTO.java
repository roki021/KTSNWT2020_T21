package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Guest;

import javax.validation.constraints.Min;

public class SubscriptionDTO {
    @Min(1)
    private int culturalOfferId;

    @Min(1)
    private int guestId;

    public SubscriptionDTO() {}

    public SubscriptionDTO(int userId, int culturalOfferId)
    {
        this.guestId = userId;
        this.culturalOfferId = culturalOfferId;
    }


    public int getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(int culturalOfferId) {
        this.culturalOfferId = culturalOfferId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
}
