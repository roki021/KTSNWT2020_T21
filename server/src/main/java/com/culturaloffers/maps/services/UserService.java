package com.culturaloffers.maps.services;

import com.culturaloffers.maps.dto.UserDTO;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private List<UserDTO> convertToList(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        for(User user : users) {
            usersDTO.add(new UserDTO(user));
        }

        return usersDTO;
    }

    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        return new UserDTO(user);
    }

    public Page<UserDTO> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return new PageImpl<UserDTO>(
                convertToList(users.getContent()),
                pageable,
                users.getTotalElements()
        );
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
