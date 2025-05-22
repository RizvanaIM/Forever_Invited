package com.foreverinvited.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foreverinvited.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByServiceId(Long serviceId);
    Optional<Rating> findByServiceIdAndUserEmail(Long serviceId, String email);
}

