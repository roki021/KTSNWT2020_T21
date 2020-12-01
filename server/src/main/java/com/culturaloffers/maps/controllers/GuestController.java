package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.GuestDTO;
import com.culturaloffers.maps.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, String>> addGuest(@RequestBody GuestDTO newGuest) {
        Map<String, String> response = new HashMap<>();
        GuestDTO addedGuest = guestService.insert(newGuest);
        if(addedGuest != null) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public Page<GuestDTO> getGuests(Pageable pageable) {
        return guestService.getGuests(pageable);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable String username) {
        Map<String, String> response = new HashMap<>();
        GuestDTO guest = guestService.getGuestByUsername(username);
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable String username, @RequestBody GuestDTO guestDTO) {
        Map<String, String> response = new HashMap<>();
        GuestDTO updatedGuest = guestService.update(username, guestDTO);
        return new ResponseEntity<>(updatedGuest, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, String>> deleteGuest(@PathVariable String username) {
        Map<String, String> response = new HashMap<>();
        if(guestService.delete(username)) {
            response.put("message", "true");
        }
        else {
            response.put("message", "false");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
