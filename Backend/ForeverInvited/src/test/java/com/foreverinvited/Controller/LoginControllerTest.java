package com.foreverinvited.Controller;

import com.foreverinvited.controller.LoginController;
import com.foreverinvited.model.User;
import com.foreverinvited.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User createUser(String role, boolean approved) {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(role);
        user.setApproved(approved);
        return user;
    }

    @Test
    void testLoginSuccess_ServiceProviderApproved() throws Exception {
        User mockUser = createUser("Service provider", true);
        when(userService.authenticateUser("test@example.com", "password123")).thenReturn(mockUser);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("Service provider"))
                .andExpect(jsonPath("$.approved").value(true));
    }

    @Test
    void testLogin_ServiceProviderNotApproved() throws Exception {
        User mockUser = createUser("Service provider", false);
        when(userService.authenticateUser("test@example.com", "password123")).thenReturn(mockUser);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Your account is awaiting admin approval."));
    }

    @Test
    void testLoginSuccess_OtherUser() throws Exception {
        User mockUser = createUser("User", true);
        when(userService.authenticateUser("test@example.com", "password123")).thenReturn(mockUser);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("User"));
    }

    @Test
    void testLoginInvalidCredentials() throws Exception {
        User inputUser = createUser("User", true);
        when(userService.authenticateUser("test@example.com", "password123")).thenReturn(null);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials."));
    }
}
