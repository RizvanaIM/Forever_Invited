package com.foreverinvited.service;


import com.foreverinvited.model.User;
import com.foreverinvited.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Authenticate user based on email and password
    public User authenticateUser(String email, String password) {
        // Find the user by email
        User user = userRepository.findById(email).orElse(null);

        // Check if the user exists and if the password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Return the user object if credentials match
        }

        return null;  // Return null if user doesn't exist or passwords don't match
    }

    // Send password reset token (you can implement an actual email service here)
    public boolean sendPasswordResetToken(String email) {
        User user = userRepository.findById(email).orElse(null);
        if (user != null) {
            // Here you'd generate and send a reset token to the user's email
            // For now, let's just return true
            System.out.println("Sending reset email to: " + email);  // Placeholder
            return true;
        }
        return false;
    }

    // Reset password using the reset token (simplified, in real scenarios you would validate the token)
    public boolean resetPassword(String token, String newPassword) {
        // For now, we are assuming token is valid
        // In real-world scenarios, you would check token expiration and validity
        User user = userRepository.findById("email_associated_with_token").orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Register a new user
    public boolean registerUser(User user) {
        // Check if the user already exists by email
        if (userRepository.existsById(user.getEmail())) {
            return false;  // User already exists
        }

        // Set approval to false for service providers (admin will approve later)
        if ("Service provider".equals(user.getRole())) {
            user.setApproved(false);
        } else if("Admin".equals(user.getRole())){
            user.setApproved(false);
        }
        else {
            user.setApproved(true);  // Auto-approve for other roles
        }
        userRepository.save(user);  // Save the user to the database
        return true;
    }
}
