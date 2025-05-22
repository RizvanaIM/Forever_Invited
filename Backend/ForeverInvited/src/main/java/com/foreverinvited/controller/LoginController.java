package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.model.User;
import com.foreverinvited.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserService userService;

    // Login Endpoint
    @PostMapping("/login")
      // Allow all origins (can restrict to frontend URL later)
    public ResponseEntity<?> login(@RequestBody User user) {
        // Authenticate the user based on email and password
        User existingUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        // Check if the user exists and validate the credentials
        if (existingUser != null) {
            // If user exists and credentials match, check the user's role and approval status
            if ("Service provider".equals(existingUser.getRole())) {
                if (!existingUser.isApproved()) {
                    return ResponseEntity.status(403).body("Your account is awaiting admin approval.");
                }
            }

            // Return user details along with role and approval status
            return ResponseEntity.ok(existingUser);  // Send back the user object with role
        } else {
            // If user does not exist or invalid credentials
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }
}
