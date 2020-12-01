package com.culturaloffers.maps.services;

import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public boolean delete(String username) {
        User user = userRepository.findByUsername(username);

        if(user != null) {
            userRepository.delete(user);

            return true;
        }

        return false;
    }
}
