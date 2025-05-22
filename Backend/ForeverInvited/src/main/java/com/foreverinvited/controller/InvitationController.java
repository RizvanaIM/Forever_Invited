package com.foreverinvited.controller;

import com.foreverinvited.model.Invitation;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.UserRepository;
import com.foreverinvited.service.InvitationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/invitation")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserRepository userRepository;

    // @PostMapping("/upload")
    // public Invitation uploadInvitation(@RequestBody Invitation invitation) {
    //     return invitationService.saveInvitation(invitation);
    // }

    @PostMapping("/upload")
public ResponseEntity<Invitation> uploadInvitation(
        @RequestParam("message") String message,
        @RequestParam("date") String date,
        @RequestParam("image") MultipartFile imageFile,
        @RequestParam("email") String email) {

    try {
        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());

        String imageUrl = "http://localhost:8080/" + uploadDir + fileName;

        User user = userRepository.findById(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Invitation invitation = invitationService.createInvitation(message, date, imageUrl, user);
        return ResponseEntity.ok(invitation);

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}




    @GetMapping("/{id}")
    public Invitation geInvitationById(@PathVariable Long id) {
        return invitationService.getInvitationById(id);
    }

    @GetMapping("/guests/{guestId}/invitation")
public ResponseEntity<Invitation> getInvitationForGuest(@PathVariable Long guestId) {
    Invitation invitation = invitationService.getInvitationByGuestId(guestId);
    if (invitation != null) {
        return ResponseEntity.ok(invitation);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
