package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank(message = "Username name cannot be empty.")
    private String username;

    @NotBlank(message = "Password name cannot be empty.")
    private String password;

    public UserDTO() {}

    public UserDTO(
            @NotBlank(message = "Username name cannot be empty.") String username,
            @NotBlank(message = "Password name cannot be empty.") String password) {
        this.username = username;
        this.password = password;
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
