package com.foreverinvited.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreverinvited.model.Guest;
import com.foreverinvited.model.User;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmailAndSecretKey(String email, String secretKey);

    Optional<Guest> findByEmail(String email);

    Set<Guest> findByUser(User user);

}
