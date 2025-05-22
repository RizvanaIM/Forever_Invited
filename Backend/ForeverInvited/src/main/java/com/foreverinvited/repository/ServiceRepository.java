package com.foreverinvited.repository;

import com.foreverinvited.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByUserEmail(String email);  // Get all services for a specific service provider
}
