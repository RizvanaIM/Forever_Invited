package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.model.Rating;

import com.foreverinvited.model.Service;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.RatingRepository;
import com.foreverinvited.repository.ServiceRepository;
import com.foreverinvited.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // Allow frontend access

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<Rating> addRating(@RequestParam Long serviceId,
                                            @RequestParam String userEmail,
                                            @RequestParam int ratingValue) {
        Optional<Rating> existing = ratingRepository.findByServiceIdAndUserEmail(serviceId, userEmail);
        if (existing.isPresent()) {
            Rating rating = existing.get();
            rating.setRatingValue(ratingValue);
            return ResponseEntity.ok(ratingRepository.save(rating));
        }

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        User user = userRepository.findByEmail(userEmail);
                

        Rating rating = new Rating();
        rating.setService(service);
        rating.setUser(user);
        rating.setRatingValue(ratingValue);

        return ResponseEntity.ok(ratingRepository.save(rating));
    }

    @GetMapping("/average/{serviceId}")
    public double getAverageRating(@PathVariable Long serviceId) {
        List<Rating> ratings = ratingRepository.findByServiceId(serviceId);
        return ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
    }
}
