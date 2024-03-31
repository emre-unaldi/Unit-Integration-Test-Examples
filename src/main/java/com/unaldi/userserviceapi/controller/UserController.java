package com.unaldi.userserviceapi.controller;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> add(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.add(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable("userId") long userId) {
        UserDTO user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<UserDTO> deleteById(@PathVariable("userId") long userId) {
        UserDTO deletedUser = userService.deleteById(userId);
        return ResponseEntity.ok(deletedUser);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<List<UserDTO>> findByUsernameContaining(@RequestParam String username){
        List<UserDTO> users = userService.findByUsernameContaining(username);
        return ResponseEntity.ok(users);
    }
}
