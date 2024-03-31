package com.unaldi.userserviceapi.business;

import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;
import com.unaldi.userserviceapi.repository.UserRepository;
import com.unaldi.userserviceapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
@ExtendWith(MockitoExtension.class)
public class BusinessTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenValidUserDTO_whenAddUser_thenUserIsAdded(){
        User user = preparedUser();
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = preparedUserDTO();
        userService.add(userDTO);

        assertTrue(
                userDTO.getId() > 0,
                "Service add record test failed !"
        );
    }

    @Test
    void givenExistingUserId_whenFindUserById_thenUserIsFound() {
        User user = preparedUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDTO foundUserDTO = userService.findById(user.getId());

        assertEquals(
                user.getId(), foundUserDTO.getId(),
                "Service find user test failed !"
        );
    }

    @Test
    void givenNonexistentUserId_whenFindUserById_thenUserNotFound() {
        User user = preparedUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(Exception.class, () -> {
            UserDTO foundUserDTO = userService.findById(-1L);
        },"Service mock not find user test failed !");
    }

    @Test
    void givenExistingUserDTO_whenUpdateUser_thenUserIsUpdated() {
        User user = preparedUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.findById(user.getId());

        String updateFirstName = "Emre 2";
        String updateLastName = "Ünaldı 2";
        String updateEmail = "emree.unaldi@gmail.com";
        String updateUsername = "eunaldi2";
        String updatePassword = "emre11892";

        user.setFirstName(updateFirstName);
        user.setLastName(updateLastName);
        user.setEmail(updateEmail);
        user.setUsername(updateUsername);
        user.setPassword(updatePassword);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userDTO.setFirstName(updateFirstName);
        userDTO.setLastName(updateLastName);
        userDTO.setEmail(updateEmail);
        userDTO.setUsername(updateUsername);
        userDTO.setPassword(updatePassword);

        userService.update(userDTO);

        UserDTO foundUserDTO = userService.findById(user.getId());

        assertTrue(
                foundUserDTO.getFirstName().equals(user.getFirstName()) &&
                foundUserDTO.getLastName().equals(user.getLastName()) &&
                foundUserDTO.getEmail().equals(user.getEmail()) &&
                foundUserDTO.getUsername().equals(user.getUsername()) &&
                foundUserDTO.getPassword().equals(user.getPassword()),
                "Service update user test failed !"
        );
    }

    @Test
    void givenExistingUserId_whenDeleteUser_thenUserIsDeleted() {
        User user = preparedUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteById(user.getId());

        verify(userRepository).deleteById(any());
    }

    @Test
    void givenUsersExist_whenFindAllUsers_thenAllUsersAreFound() {
        when(userRepository.findAll()).thenReturn(preparedUserList());

        List<UserDTO> userDTOList = userService.findAll();

        assertEquals(
                preparedUserList().size(), userDTOList.size(),
                "Service record list test failed !"
        );
    }

    @Test
    void givenUsernameSubstring_whenFindByUsernameContaining_thenUsersAreFound() {
        when(userRepository.findByUsernameContaining(any(String.class))).thenReturn(preparedUserList());

        String searchedUsername = "eunaldi1";

        List<UserDTO> userDTOList = userService.findByUsernameContaining(searchedUsername);

        assertEquals(
                3, userDTOList.size(),
                "Service custom query test failed !"
        );
    }

    private User preparedUser() {
        return new User(
                1L,
                "Emre",
                "Ünaldı",
                "emree@gmail.com",
                "eunaldi",
                "emre1189"
        );
    }

    private List<User> preparedUserList() {
        return new ArrayList<>(Arrays.asList(
                new User(1L,"Emre 1","Ünaldı 1","emree1@gmail.com","eunaldi11","emree1"),
                new User(2L,"Emre 2","Ünaldı 2","emree2@gmail.com","eunaldi12","emree2"),
                new User(3L,"Emre 3","Ünaldı 3","emree3@gmail.com","eunaldi13","emree3")
        )
        );
    }

    private UserDTO preparedUserDTO() {
        return new UserDTO(
                preparedUser().getId(),
                preparedUser().getFirstName(),
                preparedUser().getLastName(),
                preparedUser().getEmail(),
                preparedUser().getUsername(),
                preparedUser().getPassword()
        );
    }
}
