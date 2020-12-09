package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Guest findProfile(Integer id, String username) throws Exception {
        Guest user = guestRepository.findById(id).orElse(null);
        if(!user.getUsername().equals(username)){
            throw new Exception("Unauthorized action");
        }
        return user;
    }

    public Guest updateProfile(Integer id, Guest guest, String username) throws Exception {
        Guest user = guestRepository.findById(id).orElse(null);
        if(user == null){
            throw new Exception("Guest doesnt exist.");
        }

        if(!user.getUsername().equals(username)){
            throw new Exception("Unauthorized action");
        }
        if(!user.getEmailAddress().equals(guest.getEmailAddress())){
            if(guestRepository.findByEmailAddress(guest.getEmailAddress()) != null){
                throw new Exception("Email taken");
            }
        }

        if(!user.getUsername().equals(guest.getUsername())){
            if(guestRepository.findByUsername(guest.getUsername()) != null){
                throw new Exception("Username taken");
            }
        }
        user.setEmailAddress(guest.getEmailAddress());
        user.setFirstName(guest.getFirstName());
        user.setLastName(guest.getLastName());
        user.setUsername(guest.getUsername());

        guestRepository.save(user);

        return user;
    }

    public void ChangePassword(Integer id, String oldPassword, String newPassword, String username) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new Exception("User doesnt exist");
        }
        if(!user.getUsername().equals(username)){
            throw new Exception("Unauthorized changes");
        }

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new Exception("Bad password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
