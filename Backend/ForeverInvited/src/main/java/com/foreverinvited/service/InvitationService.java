package com.foreverinvited.service;

import com.foreverinvited.model.Guest;
import com.foreverinvited.model.Invitation;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.GuestRepository;
import com.foreverinvited.repository.InvitationRepository;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private GuestRepository guestRepository;

    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public Invitation getInvitationById(Long id) {
        return invitationRepository.findById(id).orElse(null);
    }

    public Invitation getInvitationByGuestId(Long guestId) {
    return invitationRepository.findByGuestId(guestId);
    }

    public Invitation createInvitation(String message, String date, String imageUrl, User user) {
        Invitation invitation = new Invitation(message, date);
        invitation.setUser(user);
        invitation.setInvitationImageUrl(imageUrl);

        Set<Guest> guests = guestRepository.findByUser(user);
        invitation.setGuests(guests);

        return invitationRepository.save(invitation);
    }



}
