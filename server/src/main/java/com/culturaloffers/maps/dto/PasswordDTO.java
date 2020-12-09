package com.culturaloffers.maps.dto;

import javax.validation.constraints.NotBlank;

public class PasswordDTO {
    @NotBlank(message = "Old password cannot be empty.")
    private String oldPassword;
    @NotBlank(message = "New password cannot be empty.")
    private String newPassword;
    @NotBlank(message = "Repeted password cannot be empty.")
    private String repetedPassword;

    public PasswordDTO(){

    }

    public PasswordDTO(@NotBlank(message = "Old password cannot be empty.") String oldPassword,
                       @NotBlank(message = "New password cannot be empty.") String newPassword,
                       @NotBlank(message = "Repeted password cannot be empty.") String repetedPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repetedPassword = repetedPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getRepetedPassword() {
        return repetedPassword;
    }

    public void setRepetedPassword(String repetedPassword) {
        this.repetedPassword = repetedPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
