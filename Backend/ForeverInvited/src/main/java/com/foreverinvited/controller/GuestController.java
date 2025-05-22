package com.foreverinvited.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.foreverinvited.model.Guest;
import com.foreverinvited.model.Invitation;
import com.foreverinvited.service.EmailService;
import com.foreverinvited.service.GuestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/add")
public Guest addGuest(@RequestBody Guest guest) {
    return guestService.addGuest(guest); // Removed email sending
}

@PostMapping("/send-invitation/{id}")
public ResponseEntity<?> sendInvitation(@PathVariable Long id) {
    Optional<Guest> optionalGuest = guestService.getGuestById(id);
    if (optionalGuest.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest not found");
    }

    Guest guest = optionalGuest.get();
    String invitationLink = "http://localhost:5173/GuestLogin";
    String body = "<p>Dear " + guest.getName() + ",</p>" +
            "<p>Click the link to view your invitation:</p>" +
            "<p><a href=\"" + invitationLink + "\">View Invitation</a></p>" +
            "<p>Your secret key is: <strong>" + guest.getSecretKey() + "</strong></p>";

    emailService.sendInvitation(guest.getEmail(), "Wedding Invitation", body);
    return ResponseEntity.ok("Invitation sent to " + guest.getEmail());
    }

    // @PostMapping("/upload-invitation/{guestId}")
    // public ResponseEntity<?> uploadInvitation(@PathVariable Long guestId,
    //                                           @RequestParam("message") String message,
    //                                           @RequestParam("date") String date,
    //                                           @RequestParam("image") MultipartFile image) {
    //     try {
    //         guestService.saveInvitationDetails(guestId, message, date, image);
    //         return ResponseEntity.ok("Invitation uploaded successfully");
    //     } catch (IOException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload invitation");
    //     }
    // }


    @PutMapping("/update/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        return guestService.updateGuest(id, guestDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }

    @GetMapping("/")
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginGuest(@RequestBody Guest guest) {
        Optional<Guest> authenticatedGuest = guestService.authenticateGuest(guest.getEmail(), guest.getSecretKey());
        if (authenticatedGuest.isPresent()) {
            Guest foundGuest = authenticatedGuest.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", foundGuest.getId());
            // response.put("invitation", new Invitation(foundGuest.getInvitationMessage(), foundGuest.getWeddingDate()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/{guestId}/invitation")
public ResponseEntity<?> getInvitationsForGuest(@PathVariable Long guestId) {
    Optional<Guest> guest = guestService.getGuestById(guestId);
    if (guest.isPresent()) {
        Set<Invitation> invitations = guest.get().getInvitations();
        return ResponseEntity.ok(invitations);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest not found");
    }
}
}

