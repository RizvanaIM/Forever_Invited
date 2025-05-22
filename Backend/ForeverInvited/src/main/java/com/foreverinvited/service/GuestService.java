package com.foreverinvited.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.foreverinvited.model.Guest;
import com.foreverinvited.repository.GuestRepository;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    private final String uploadDir = "uploads/";

    public Guest addGuest(Guest guest) {
        guest.setSecretKey(generateSecretKey());
        return guestRepository.save(guest);
    }

    public Guest updateGuest(Long id, Guest guestDetails) {
        Guest guest = guestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Guest not found"));
        guest.setName(guestDetails.getName());
        guest.setEmail(guestDetails.getEmail());
        return guestRepository.save(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    private String generateSecretKey() {
        // Implement secret key generation logic
        return UUID.randomUUID().toString();
    }

    public Optional<Guest> authenticateGuest(String email, String secretKey) {
        return guestRepository.findByEmailAndSecretKey(email, secretKey);
    }

    public Optional<Guest> getGuestById(Long id) {
    return guestRepository.findById(id);
    }

    // public void saveInvitationDetails(Long guestId, String message, String date, MultipartFile imageFile) throws IOException {
    //     Guest guest = guestRepository.findById(guestId)
    //         .orElseThrow(() -> new RuntimeException("Guest not found"));

    //     guest.set(message);
    //     guest.setWeddingDate(date);

    //     if (imageFile != null && !imageFile.isEmpty()) {
    //         String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
    //         File dest = new File(uploadDir + fileName);
    //         dest.getParentFile().mkdirs();
    //         imageFile.transferTo(dest);
    //         guest.setInvitationImageUrl(fileName);
    //     }

    //     guestRepository.save(guest);
    // }

}
