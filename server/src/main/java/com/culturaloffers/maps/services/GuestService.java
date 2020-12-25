package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Authority;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public Guest getGuestByUsername(String username) {
        return guestRepository.findByUsername(username);
    }

    public Guest getGuestByEmailAddress(String emailAddress) {
        return guestRepository.findByEmailAddress(emailAddress);
    }

    public Guest getGuestById(int id) {
        return guestRepository.findById(id).orElse(null);
    }

    public List<Guest> getAllGuests() { return guestRepository.findAll(); }

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
        if(checkUsername(newGuest.getUsername()) && checkEmailAddress(newGuest.getEmailAddress())) {
            // before password is placed it goes through hashing phase
            newGuest.setPassword(passwordEncoder.encode(newGuest.getPassword()));

            List<Authority> auth = authService.findByName("ROLE_GUEST");
            newGuest.setAuthorities(auth);
            return guestRepository.save(newGuest);
        }

        return null;
    }

    public Guest update(Integer id, Guest newGuest) {
        Guest guest = guestRepository.findById(id).orElse(null);

        if(guest != null) {
            User userOne = userRepository.findByUsername(newGuest.getUsername());
            User userTwo = userRepository.findByEmailAddress(newGuest.getEmailAddress());
            if(!((userOne != null && userOne.getId().intValue() != id) ||
                (userTwo != null && userTwo.getId().intValue() != id))) {
                guest.setFirstName(newGuest.getFirstName());
                guest.setLastName(newGuest.getLastName());
                guest.setEmailAddress(newGuest.getEmailAddress());
                guest.setUsername(newGuest.getUsername());
                guest.setPassword(newGuest.getPassword());

                return guestRepository.save(guest);
            }
        }

        return null;
    }

    private boolean checkUsername(String username) {
        return userRepository.findByUsername(username) == null;
    }

    private boolean checkEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress) == null;
    }
}
