package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.GuestRepository;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private UserRepository userRepository;
    private GuestRepository guestRepository;

    public Guest findProfile(Integer id){
        return  guestRepository.findById(id).orElse(null);
    }

    public Guest updateProfile(Guest guest) throws Exception {
        Guest user = guestRepository.findById(guest.getId()).orElse(null);
        if(user == null){
            throw new Exception("Guest doesnt exist.");
        }

        user.setEmailAddress(guest.getEmailAddress());
        user.setFirstName(guest.getFirstName());
        user.setLastName(guest.getLastName());
        user.setUsername(guest.getUsername());

        guestRepository.save(user);

        return user;
    }

    public void ChangePassword(Integer id, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new Exception("User doesnt exist");
        }
        if(!user.getPassword().equals(oldPassword)){
            throw new Exception("Bad password");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
