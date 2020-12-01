package com.culturaloffers.maps.dto;

import com.culturaloffers.maps.model.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestDTO {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String username;
    private String password;

    public GuestDTO() {}

    public GuestDTO(Guest guest) {
        if(guest != null) {
            this.username = guest.getUsername();
            this.password = guest.getPassword();
            this.emailAddress = guest.getEmailAddress();
            this.firstName = guest.getFirstName();
            this.lastName = guest.getLastName();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
