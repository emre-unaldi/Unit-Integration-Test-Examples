package com.unaldi.userserviceapi.mapper;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;
import com.unaldi.userserviceapi.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */

@SpringBootTest
public class MapperTests {

    @Test
    public void testUserDtoToUserMapping() {
        UserDTO dto = new UserDTO(
                1L,
                "Emre",
                "Ünaldı",
                "emree.unaldi@gmail.com",
                "eunaldi",
                "121221"
        );

        User entity = UserMapper.INSTANCE.convertToUser(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getPassword(), entity.getPassword());
    }

    @Test
    public void testUserToUserDtoMapping() {
        User entity = new User(
                1L,
                "Emre",
                "Ünaldı",
                "emree.unaldi@gmail.com",
                "eunaldi",
                "121221"
        );

        UserDTO dto = UserMapper.INSTANCE.convertToUserDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getPassword(), dto.getPassword());
    }

    @Test
    public void testUsersToUserDtosMapping() {
        List<User> userList = new ArrayList<>(Arrays.asList(
                new User(
                        1L,
                        "Emre",
                        "Ünaldı",
                        "emree.unaldi@gmail.com",
                        "eunaldi",
                        "121221"
                ),
                new User(
                        2L,
                        "Emre 2",
                        "Ünaldı 2",
                        "emree@gmail.com",
                        "eunaldi2",
                        "1212213"
                )
        ));

        List<UserDTO> userDTOList = UserMapper.INSTANCE.convertToUserDTOs(userList);

        assertEquals(userList.get(0).getId(), userDTOList.get(0).getId());
        assertEquals(userList.get(1).getId(), userDTOList.get(1).getId());
    }
}
