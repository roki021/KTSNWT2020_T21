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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    private GuestMapper guestMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
        public ResponseEntity<GuestDTO> addGuest(@Valid @RequestBody GuestDTO guestDTO) {
        Guest addedGuest;
        try {
            addedGuest = guestService.insert(guestMapper.toEntity(guestDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(guestMapper.toDto(addedGuest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<GuestDTO> getGuests(Pageable pageable) {
        Page<Guest> users = guestService.getGuests(pageable);
        return new PageImpl<GuestDTO>(
                guestMapper.toDtoList(users.getContent()),
                pageable,
                users.getTotalElements()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable int id) {
        Guest guest = guestService.getGuestById(id);
        if(guest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Integer id, @Valid @RequestBody GuestDTO guestDTO) {
        Guest guest;
        try {
            guest = guestService.update(id, guestMapper.toEntity(guestDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guestMapper.toDto(guest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Integer id) {
        try {
            if(guestService.delete(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public GuestController() {
        this.guestMapper = new GuestMapper();
    }
}
