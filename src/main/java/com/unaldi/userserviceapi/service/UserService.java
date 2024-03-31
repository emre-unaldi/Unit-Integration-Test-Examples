package com.unaldi.userserviceapi.service;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;

import java.util.List;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
public interface UserService {
    UserDTO add(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    UserDTO deleteById(long userId);
    UserDTO findById(Long userId);
    List<UserDTO> findAll();
    List<UserDTO> findByUsernameContaining(String username);
}
