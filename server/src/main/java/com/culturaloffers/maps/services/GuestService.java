package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    private List<GuestDTO> convertToList(List<Guest> guests) {
        List<GuestDTO> guestsDTO = new ArrayList<GuestDTO>();
        for(Guest guest : guests) {
            guestsDTO.add(new GuestDTO(guest));
        }

        return guestsDTO;
    }

    public GuestDTO getGuestByUsername(String username) {
        Guest guest = guestRepository.findByUsername(username);
        return new GuestDTO(guest);
    }

    public GuestDTO getGuestByEmailAddress(String emailAddress) {
        Guest guest = guestRepository.findByEmailAddress(emailAddress);
        return new GuestDTO(guest);
    }

    public Page<GuestDTO> getGuests(Pageable pageable) {
        Page<Guest> guests = guestRepository.findAll(pageable);
        return new PageImpl<GuestDTO>(
                convertToList(guests.getContent()),
                pageable,
                guests.getTotalElements()
        );
    }

    public boolean delete(String username) {
        Guest guest = guestRepository.findByUsername(username);

        if(guest != null) {
            guestRepository.delete(guest);

            return true;
        }

        return false;
    }

    public GuestDTO insert(GuestDTO guestDTO) {
        Guest guest = guestRepository.findByUsername(guestDTO.getUsername());

        if(guest == null) {
            guest = new Guest();
            guest.setFirstName(guestDTO.getFirstName());
            guest.setLastName(guestDTO.getLastName());
            guest.setEmailAddress(guestDTO.getEmailAddress());
            guest.setUsername(guestDTO.getUsername());
            guest.setPassword(guestDTO.getPassword());

            return new GuestDTO(guestRepository.save(guest));
        }

        return null;
    }

    public GuestDTO update(String username, GuestDTO guestDTO) {
        Guest guest = guestRepository.findByUsername(username);

        if(guest != null) {
            guest.setFirstName(guestDTO.getFirstName());
            guest.setLastName(guestDTO.getLastName());
            guest.setEmailAddress(guestDTO.getEmailAddress());
            guest.setUsername(guestDTO.getUsername());
            guest.setPassword(guestDTO.getPassword());

            return new GuestDTO(guestRepository.save(guest));
        }

        return null;
    }
}
