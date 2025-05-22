package com.foreverinvited.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.foreverinvited.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}

