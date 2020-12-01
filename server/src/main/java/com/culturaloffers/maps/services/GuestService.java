package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Guest getGuestByUsername(String username) {
        return guestRepository.findByUsername(username);
    }

    public Guest getGuestByEmailAddress(String emailAddress) {
        return guestRepository.findByEmailAddress(emailAddress);
    }

    public Page<Guest> getGuests(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }

    public boolean delete(String username) {
        Guest guest = guestRepository.findByUsername(username);

        if(guest != null) {
            guestRepository.delete(guest);

            return true;
        }

        return false;
    }

    public Guest insert(Guest newGuest) {
        Guest guest = guestRepository.findByUsername(newGuest.getUsername());
        if(guest == null)
            return guestRepository.save(newGuest);

        return null;
    }

    public Guest update(String username, Guest newGuest) {
        Guest guest = guestRepository.findByUsername(username);

        if(guest != null) {
            guest.setFirstName(newGuest.getFirstName());
            guest.setLastName(newGuest.getLastName());
            guest.setEmailAddress(newGuest.getEmailAddress());
            guest.setUsername(newGuest.getUsername());
            guest.setPassword(newGuest.getPassword());

            return guestRepository.save(guest);
        }

        return null;
    }
}
