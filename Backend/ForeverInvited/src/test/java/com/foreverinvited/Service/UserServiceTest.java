package com.foreverinvited.Service;

import com.foreverinvited.model.User;
import com.foreverinvited.repository.UserRepository;
import com.foreverinvited.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        // Manually inject mock since field is private
        userService = new UserService() {
            {
                try {
                    var field = UserService.class.getDeclaredField("userRepository");
                    field.setAccessible(true);
                    field.set(this, userRepository);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void testAuthenticateUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.authenticateUser("test@example.com", "password123");
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testAuthenticateUser_WrongPassword() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.authenticateUser("test@example.com", "wrongpass");
        assertNull(result);
    }

    @Test
    void testAuthenticateUser_NotFound() {
        when(userRepository.findById("notfound@example.com")).thenReturn(Optional.empty());

        User result = userService.authenticateUser("notfound@example.com", "password");
        assertNull(result);
    }

    @Test
    void testSendPasswordResetToken_Success() {
        User user = new User();
        user.setEmail("reset@example.com");

        when(userRepository.findById("reset@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.sendPasswordResetToken("reset@example.com");
        assertTrue(result);
    }

    @Test
    void testSendPasswordResetToken_UserNotFound() {
        when(userRepository.findById("missing@example.com")).thenReturn(Optional.empty());

        boolean result = userService.sendPasswordResetToken("missing@example.com");
        assertFalse(result);
    }

    @Test
    void testResetPassword_Success() {
        User user = new User();
        user.setEmail("email_associated_with_token");
        user.setPassword("oldpass");

        when(userRepository.findById("email_associated_with_token")).thenReturn(Optional.of(user));

        boolean result = userService.resetPassword("some-token", "newpass");
        assertTrue(result);
        verify(userRepository, times(1)).save(user);
        assertEquals("newpass", user.getPassword());
    }

    @Test
    void testResetPassword_UserNotFound() {
        when(userRepository.findById("email_associated_with_token")).thenReturn(Optional.empty());

        boolean result = userService.resetPassword("some-token", "newpass");
        assertFalse(result);
    }

    @Test
    void testRegisterUser_NewUser_ServiceProvider() {
        User user = new User();
        user.setEmail("provider@example.com");
        user.setRole("Service provider");

        when(userRepository.existsById(user.getEmail())).thenReturn(false);

        boolean result = userService.registerUser(user);
        assertTrue(result);
        assertFalse(user.isApproved());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_NewUser_Admin() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole("Admin");

        when(userRepository.existsById(user.getEmail())).thenReturn(false);

        boolean result = userService.registerUser(user);
        assertTrue(result);
        assertFalse(user.isApproved());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_NewUser_OtherRole() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setRole("Guest");

        when(userRepository.existsById(user.getEmail())).thenReturn(false);

        boolean result = userService.registerUser(user);
        assertTrue(result);
        assertTrue(user.isApproved());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_AlreadyExists() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.existsById(user.getEmail())).thenReturn(true);

        boolean result = userService.registerUser(user);
        assertFalse(result);
        verify(userRepository, never()).save(any());
    }
}
