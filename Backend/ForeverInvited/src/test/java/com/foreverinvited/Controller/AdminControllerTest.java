package com.foreverinvited.Controller;

import com.foreverinvited.controller.AdminController;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;


import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private User serviceProvider;

    @BeforeEach
    void setUp() {
        serviceProvider = new User();
        serviceProvider.setEmail("provider@example.com");
        serviceProvider.setName("Provider");
        serviceProvider.setRole("Service Provider");
        serviceProvider.setApproved(false);
    }

    @Test
    void testApproveUser_Success() throws Exception {
        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(serviceProvider));
        when(userRepository.save(any(User.class))).thenReturn(serviceProvider);

        mockMvc.perform(post("/api/admin/approve")
                        .param("email", "provider@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service Provider approved successfully."));

        verify(userRepository, times(1)).save(serviceProvider);
    }

    @Test
    void testApproveUser_AlreadyApproved() throws Exception {
        serviceProvider.setApproved(true);
        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(serviceProvider));

        mockMvc.perform(post("/api/admin/approve")
                        .param("email", "provider@example.com"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found or already approved."));
    }

    @Test
    void testRejectUser_Success() throws Exception {
        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(serviceProvider));

        mockMvc.perform(post("/api/admin/reject")
                        .param("email", "provider@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service Provider rejected and removed."));

        verify(userRepository, times(1)).delete(serviceProvider);
    }

    @Test
    void testRejectUser_AlreadyApproved() throws Exception {
        serviceProvider.setApproved(true);
        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(serviceProvider));

        mockMvc.perform(post("/api/admin/reject")
                        .param("email", "provider@example.com"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found or already approved."));
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setRole("Service Provider");

        when(userRepository.findAll()).thenReturn(Arrays.asList(serviceProvider, user2));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("provider@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }
}
