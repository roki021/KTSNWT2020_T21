package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.dto.OfferTypeDTO;
import com.culturaloffers.maps.dto.PasswordDTO;
import com.culturaloffers.maps.helper.GuestMapper;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.OfferType;
import com.culturaloffers.maps.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileDataController {

    @Autowired
    ProfileService profileService;

    private GuestMapper guestMapper;

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<GuestDTO> getGuestProfile(@PathVariable Integer id,Principal principal){
        Guest guest = null;
        try {
            guest = profileService.findProfile(id, principal.getName());
        } catch (Exception e) {
            if(e.getMessage() != null) {
                if (e.getMessage().equals("Guest doesnt exist."))
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                else if (e.getMessage().equals("Unauthorized action"))
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        /*if(guest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<GuestDTO> updateGuestProfile(@PathVariable Integer id, @Valid @RequestBody GuestDTO guestDTO,
                                                       Principal principal){
        Guest guest = null;
        try {
            guest = profileService.updateProfile(id, guestMapper.toEntity(guestDTO), principal.getName());
        } catch (Exception e) {
            if(e.getMessage() != null) {
                if (e.getMessage().equals("Guest doesnt exist."))
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                else if (e.getMessage().equals("Unauthorized action"))
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_ADMIN')")
    @RequestMapping(value="/{id}/change-password", method= RequestMethod.PUT)
    public ResponseEntity<Void> changePassword(@PathVariable Integer id,@Valid @RequestBody PasswordDTO passwordDTO,
                                               Principal principal){
        try {
            if(!passwordDTO.getNewPassword().equals(passwordDTO.getRepetedPassword())){
                throw new Exception("Repeted password doesnt match");
            }
            profileService.ChangePassword(id, passwordDTO.getOldPassword(),
                    passwordDTO.getNewPassword(), principal.getName());
        } catch (Exception e) {
            if(e.getMessage() != null){
                if(e.getMessage().equals("User doesnt exist")) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else if(e.getMessage().equals("Unauthorized changes")){
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ProfileDataController() {
        this.guestMapper = new GuestMapper();
    }
}
