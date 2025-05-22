package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.service.UserService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    // Reset Password Endpoint
    @PostMapping("/reset-password")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean isSuccess = userService.resetPassword(token, newPassword);

        if (isSuccess) {
            return ResponseEntity.ok().body("Password reset successfully");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired token");
        }
    }
}
