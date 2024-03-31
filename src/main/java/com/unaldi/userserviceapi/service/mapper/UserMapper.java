package com.unaldi.userserviceapi.service.mapper;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    // Mapper Singleton
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "userDTO.id")
    @Mapping(target = "firstName", source = "userDTO.firstName")
    @Mapping(target = "lastName", source = "userDTO.lastName")
    @Mapping(target = "email", source = "userDTO.email")
    @Mapping(target = "username", source = "userDTO.username")
    @Mapping(target = "password", source = "userDTO.password")
    User convertToUser(UserDTO userDTO);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    UserDTO convertToUserDto(User user);

    List<UserDTO> convertToUserDTOs(List<User> userList);
}
