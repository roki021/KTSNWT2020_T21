package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.helper.GuestMapper;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    private GuestMapper guestMapper;

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
        public ResponseEntity<GuestDTO> addGuest(@RequestBody GuestDTO guestDTO) {
        Guest addedGuest;
        try {
            addedGuest = guestService.insert(guestMapper.toEntity(guestDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(guestMapper.toDto(addedGuest), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<GuestDTO> getGuests(Pageable pageable) {
        Page<Guest> users = guestService.getGuests(pageable);
        return new PageImpl<GuestDTO>(
                guestMapper.toDtoList(users.getContent()),
                pageable,
                users.getTotalElements()
        );
    }

    @GetMapping("/{username}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable String username) {
        Guest guest = guestService.getGuestByUsername(username);
        if(guest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable String username, @RequestBody GuestDTO guestDTO) {
        Guest guest;
        try {
            guest = guestService.update(username, guestMapper.toEntity(guestDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteGuest(@PathVariable String username) {
        try {
            guestService.delete(username);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public GuestController() {
        this.guestMapper = new GuestMapper();
    }
}
