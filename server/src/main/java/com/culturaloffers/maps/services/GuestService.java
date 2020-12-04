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

    public Guest getGuestById(int id) {
        return guestRepository.findById(id).orElse(null);
    }

    public Page<Guest> getGuests(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }

    public boolean delete(Integer id) {
        Guest guest = guestRepository.findById(id).orElse(null);

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

    public Guest update(Integer id, Guest newGuest) {
        Guest guest = guestRepository.findById(id).orElse(null);

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
