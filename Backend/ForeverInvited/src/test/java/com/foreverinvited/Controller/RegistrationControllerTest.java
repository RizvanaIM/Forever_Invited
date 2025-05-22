package com.foreverinvited.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreverinvited.controller.RegistrationController;
import com.foreverinvited.model.User;
import com.foreverinvited.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setName("Test User");
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        Mockito.when(userService.registerUser(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testRegisterUser_Failure() throws Exception {
        Mockito.when(userService.registerUser(any(User.class))).thenReturn(false);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Registration failed"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testAdminLogin_Success() throws Exception {
        mockMvc.perform(post("/api/admin/login")
                        .param("email", "rizvanaisam@gmail.com")
                        .param("password", "123@riz"))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin login successful"));
    }

    @Test
    void testAdminLogin_Failure() throws Exception {
        mockMvc.perform(post("/api/admin/login")
                        .param("email", "wrong@example.com")
                        .param("password", "wrongpass"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid admin credentials"));
    }
}
