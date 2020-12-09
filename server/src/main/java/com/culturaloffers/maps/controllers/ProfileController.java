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
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    private GuestMapper guestMapper;
    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<GuestDTO> getGuestProfile(@PathVariable Integer id){
        Guest guest = profileService.findProfile(id);
        if(guest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<GuestDTO> updateGuestProfile(@PathVariable Integer id, @RequestBody GuestDTO guestDTO){
        Guest guest = null;
        try {
            guest = profileService.updateProfile(guestMapper.toEntity(guestDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Void> changePassword(@PathVariable Integer id, @RequestBody PasswordDTO passwordDTO){
        try {
            if(!passwordDTO.getNewPassword().equals(passwordDTO.getRepetedPassword())){
                throw new Exception("Repeted password doesnt match");
            }
            profileService.ChangePassword(id, passwordDTO.getOldPassword(),passwordDTO.getNewPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ProfileController() {
        this.guestMapper = new GuestMapper();
    }
}
