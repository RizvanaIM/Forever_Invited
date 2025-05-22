package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.service.UserService;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    // Forgot Password Endpoint
    @PostMapping("/forgot-password")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        boolean isSuccess = userService.sendPasswordResetToken(email);

        if (isSuccess) {
            return ResponseEntity.ok().body("Password reset email sent");
        } else {
            return ResponseEntity.status(404).body("User with that email not found");
        }
    }
}
