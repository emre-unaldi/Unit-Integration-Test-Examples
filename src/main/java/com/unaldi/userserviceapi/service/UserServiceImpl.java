package com.unaldi.userserviceapi.service;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;
import com.unaldi.userserviceapi.repository.UserRepository;
import com.unaldi.userserviceapi.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.convertToUser(userDTO);
        userRepository.save(user);

        return UserMapper.INSTANCE.convertToUserDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.convertToUser(userDTO);
        userRepository.save(user);

        return UserMapper.INSTANCE.convertToUserDto(user);
    }

    @Override
    public UserDTO deleteById(long userId) {
        UserDTO userDTO = userRepository
                .findById(userId)
                .map(UserMapper.INSTANCE::convertToUserDto)
                .orElseThrow();

        userRepository.deleteById(userDTO.getId());

        return userDTO;
    }

    @Override
    public UserDTO findById(Long userId) {
        return userRepository
                .findById(userId)
                .map(UserMapper.INSTANCE::convertToUserDto)
                .orElseThrow();
    }

    @Override
    public List<UserDTO> findAll() {
        return UserMapper.INSTANCE.convertToUserDTOs(userRepository.findAll());
    }

    @Override
    public List<UserDTO> findByUsernameContaining(String username) {
        List<User> userList = userRepository.findByUsernameContaining(username);

        return UserMapper.INSTANCE.convertToUserDTOs(userList);
    }
}
