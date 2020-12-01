package com.culturaloffers.maps.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class GuestDTO {

    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @NotBlank(message = "Email name cannot be empty.")
    @Email(message = "Email format is not valid.")
    private String emailAddress;

    @NotBlank(message = "Username name cannot be empty.")
    private String username;

    @NotBlank(message = "Password name cannot be empty.")
    private String password;

    public GuestDTO() {}

    public GuestDTO(
            @NotBlank(message = "First name cannot be empty.") String firstName,
            @NotBlank(message = "Last name cannot be empty.") String lastName,
            @NotBlank(message = "Email name cannot be empty.")
            @Email(message = "Email format is not valid.") String emailAddress,
            @NotBlank(message = "Username name cannot be empty.") String username,
            @NotBlank(message = "Password name cannot be empty.") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
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
