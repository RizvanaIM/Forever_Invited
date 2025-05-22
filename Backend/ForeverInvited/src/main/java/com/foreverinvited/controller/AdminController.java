package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.model.User;
import com.foreverinvited.repository.UserRepository;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Admin approves service provider
@PostMapping("/admin/approve")
public ResponseEntity<String> approveUser(@RequestParam String email) {
    Optional<User> userOptional = userRepository.findById(email);
    User user = userOptional.orElse(null);

    if (user != null && (user.getRole().equalsIgnoreCase("Service provider") || user.getRole().equalsIgnoreCase("Admin")) && !user.isApproved()) {
        user.setApproved(true);
        userRepository.save(user);
        return ResponseEntity.ok(user.getRole() + " approved successfully.");
    } else {
        return ResponseEntity.badRequest().body("User not found or already approved.");
    }
}

@PostMapping("/admin/reject")
public ResponseEntity<String> rejectUser(@RequestParam String email) {
    Optional<User> userOptional = userRepository.findById(email);
    User user = userOptional.orElse(null);

    if (user != null && (user.getRole().equalsIgnoreCase("Service provider") || user.getRole().equalsIgnoreCase("Admin")) && !user.isApproved()) {
        userRepository.delete(user);
        return ResponseEntity.ok(user.getRole() + " rejected and removed.");
    } else {
        return ResponseEntity.badRequest().body("User not found or already approved.");
    }
}


    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
}

    
}
