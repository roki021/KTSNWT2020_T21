package com.culturaloffers.maps.controllers;

import com.culturaloffers.maps.dto.UserDTO;
import com.culturaloffers.maps.helper.UserMapper;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable) {
        Page<User> users = userService.getUsers(pageable);
        return ResponseEntity.ok(userMapper.toDtoList(users.getContent()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            if(userService.delete(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public UserController() {
        this.userMapper = new UserMapper();
    }
}
