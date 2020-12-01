package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String username;
    private String password;

    public UserDTO() {}

    public UserDTO(User user) {
        if(user != null) {
            this.username = user.getUsername();
            this.password = user.getPassword();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
