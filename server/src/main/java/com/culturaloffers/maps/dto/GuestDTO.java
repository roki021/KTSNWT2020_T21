package com.culturaloffers.maps.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class GuestDTO {

    private Integer id;

    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @NotBlank(message = "Email name cannot be empty.")
    @Email(message = "Email format is not valid.")
    private String emailAddress;

    @NotBlank(message = "Username name cannot be empty.")
    private String username;

    private String password;

    public GuestDTO() {}

    public GuestDTO(
            Integer id,
            @NotBlank(message = "First name cannot be empty.") String firstName,
            @NotBlank(message = "Last name cannot be empty.") String lastName,
            @NotBlank(message = "Email name cannot be empty.")
            @Email(message = "Email format is not valid.") String emailAddress,
            @NotBlank(message = "Username name cannot be empty.") String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
