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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public boolean delete(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null) {
            userRepository.delete(user);
            return true;
        }

        return false;
    }
}
