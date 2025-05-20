package com.foreverinvited.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.foreverinvited.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    // Find user by email (since email is the primary key)
}
