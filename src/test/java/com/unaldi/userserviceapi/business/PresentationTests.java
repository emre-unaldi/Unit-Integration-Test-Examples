package com.unaldi.userserviceapi.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unaldi.userserviceapi.controller.UserController;
import com.unaldi.userserviceapi.entity.Dto.UserDTO;
import com.unaldi.userserviceapi.entity.User;
import com.unaldi.userserviceapi.repository.UserRepository;
import com.unaldi.userserviceapi.service.UserService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 */
@WebMvcTest({
        UserController.class, UserService.class
})
public class PresentationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenValidUser_whenInserting_thenSuccess() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(preparedUser());

        MvcResult mvcResult = mockMvc
                .perform(
                        post("/api/v1/users/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(preparedUserDTO()))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(preparedUser().getId()))
                .andReturn();

        JsonNode jsonNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        long resultId = jsonNode.get("id").asLong();

        assertEquals(resultId, preparedUser().getId(), "User create test failed !");
    }

    @Test
    public void givenExistingUser_whenUpdating_thenSuccess() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(preparedUser());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(preparedUser()));

        MvcResult createResult = mockMvc
                .perform(
                        post("/api/v1/users/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(preparedUserDTO()))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(preparedUser().getId()))
                .andReturn();

        JsonNode jsonNode = mapper.readTree(createResult.getResponse().getContentAsString());
        long createId = jsonNode.get("id").asLong();

        MvcResult findResult = mockMvc
                .perform(
                        get("/api/v1/users/findById/{userId}", preparedUser().getId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(preparedUser().getId()))
                .andReturn();

        UserDTO findUserDTO = mapper.readValue(findResult.getResponse().getContentAsString(), UserDTO.class);

        String updateFirstName = "Emre 2";
        String updateLastName = "Ünaldı 2";
        String updateEmail = "emree.unaldi@gmail.com";
        String updateUsername = "eunaldi2";
        String updatePassword = "emre11892";

        User updateUser = new User(createId, updateFirstName, updateLastName, updateEmail, updateUsername, updatePassword);

        when(userRepository.findById(preparedUser().getId())).thenReturn(Optional.of(updateUser));

        findUserDTO.setFirstName(updateFirstName);
        findUserDTO.setLastName(updateLastName);
        findUserDTO.setEmail(updateEmail);
        findUserDTO.setUsername(updateUsername);
        findUserDTO.setPassword(updatePassword);

        mockMvc
                .perform(
                        put("/api/v1/users/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updateUser))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createId));


        mockMvc
                .perform(
                        get("/api/v1/users/findById/{userId}", createId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createId))
                .andExpect(jsonPath("$.firstName").value(updateFirstName))
                .andExpect(jsonPath("$.lastName").value(updateLastName))
                .andExpect(jsonPath("$.email").value(updateEmail))
                .andExpect(jsonPath("$.username").value(updateUsername))
                .andExpect(jsonPath("$.password").value(updatePassword));
    }

    @Test
    public void givenExistingUser_whenFinding_thenSuccess() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(preparedUser()));

        mockMvc
                .perform(
                        get("/api/v1/users/findById/{userId}", preparedUser().getId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(preparedUser().getId()))
                .andExpect(jsonPath("$.firstName").value(preparedUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(preparedUser().getLastName()))
                .andExpect(jsonPath("$.email").value(preparedUser().getEmail()))
                .andExpect(jsonPath("$.username").value(preparedUser().getUsername()))
                .andExpect(jsonPath("$.password").value(preparedUser().getPassword()));
    }

    @Test
    public void givenNonExistingUser_whenFinding_thenInternalServerError() {
        when(userRepository.findById(preparedUser().getId())).thenReturn(Optional.of(preparedUser()));

        long nonExistingId = -1L;

        assertThrows(ServletException.class, () -> {
            mockMvc
                    .perform(
                            get("/api/v1/users/findById/{userId}", nonExistingId)
                                    .accept(MediaType.APPLICATION_JSON)
                    );
        }, "User not find test failed !");
    }

    @Test
    public void givenUserList_whenListing_thenSuccess() throws Exception {
        when(userRepository.findAll()).thenReturn(preparedUserList());

        mockMvc
                .perform(
                        get("/api/v1/users/findAll")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(preparedUserList().size()))
                .andExpect(jsonPath("$.[0].id").value(preparedUserList().get(0).getId()))
                .andExpect(jsonPath("$.[1].id").value(preparedUserList().get(1).getId()))
                .andExpect(jsonPath("$.[2].id").value(preparedUserList().get(2).getId()));
    }

   @Test
   public void givenUserId_whenDeleting_thenSuccess() throws Exception {
       when(userRepository.findById(preparedUser().getId())).thenReturn(Optional.of(preparedUser()));

       mockMvc
               .perform(
                       delete("/api/v1/users/deleteById/{userId}", preparedUser().getId())
                               .accept(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(preparedUser().getId()));
   }

   @Test
    public void givenPurchaseList_whenFindByUsernameQuerying_thenSuccess() throws Exception {
       String searchedUsername = "eunaldi1";

       List<User> filteredUserList = preparedUserList()
               .stream()
               .filter(user -> user.getUsername().contains(searchedUsername))
               .toList();

       when(userRepository.findByUsernameContaining(any(String.class))).thenReturn(filteredUserList);

       mockMvc
               .perform(
                       get("/api/v1/users/findByUsername?username={searchedUsername}", searchedUsername)
                               .accept(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(filteredUserList.size()));
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
                new User(1L,"Emre 1","Ünaldı 1","emree1@gmail.com","eunaldi3846","emree1"),
                new User(2L,"Emre 2","Ünaldı 2","emree2@gmail.com","eunaldi3338","emree2"),
                new User(3L,"Emre 3","Ünaldı 3","emree3@gmail.com","eunaldi1189","emree3")
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
