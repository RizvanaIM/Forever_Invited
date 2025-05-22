package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.model.User;
import com.foreverinvited.service.UserService;

@RestController
@RequestMapping("/api")  // Base URL for all endpoints
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Register user endpoint with CORS for all origins
    @PostMapping("/register")
    @CrossOrigin(origins = "*")  // Allow all origins to access this endpoint
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        boolean isSuccess = userService.registerUser(user);
        if (isSuccess) {
            return ResponseEntity.ok().body(new ResponseMessage("Registration successful", true));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Registration failed", false));
        }
    }

    // Admin login endpoint for demonstration purposes
    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestParam String email, @RequestParam String password) {
        if (email.equals("rizvanaisam@gmail.com") && password.equals("123@riz")) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid admin credentials");
        }
    }
}

class ResponseMessage {
    private String message;
    private boolean success;

    public ResponseMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
