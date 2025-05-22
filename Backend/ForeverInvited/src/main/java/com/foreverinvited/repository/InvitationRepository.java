package com.foreverinvited.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreverinvited.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByGuestId(Long guestId);
}
