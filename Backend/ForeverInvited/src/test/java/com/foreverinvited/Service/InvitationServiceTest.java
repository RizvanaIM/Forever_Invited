package com.foreverinvited.Service;

import com.foreverinvited.model.Guest;
import com.foreverinvited.model.Invitation;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.GuestRepository;
import com.foreverinvited.repository.InvitationRepository;
import com.foreverinvited.service.InvitationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private InvitationService invitationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInvitation() {
        Invitation invitation = new Invitation("Message", "2025-05-25");
        when(invitationRepository.save(invitation)).thenReturn(invitation);

        Invitation result = invitationService.saveInvitation(invitation);

        assertNotNull(result);
        assertEquals("Message", result.getInvitationMessage());
        verify(invitationRepository).save(invitation);
    }

    @Test
    void testGetInvitationById() {
        Invitation invitation = new Invitation("Message", "2025-05-25");
        when(invitationRepository.findById(1L)).thenReturn(Optional.of(invitation));

        Invitation result = invitationService.getInvitationById(1L);

        assertNotNull(result);
        assertEquals("Message", result.getInvitationMessage());
        verify(invitationRepository).findById(1L);
    }

    @Test
    void testCreateInvitation() {
        User user = new User();
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        Set<Guest> guests = new HashSet<>(List.of(guest1, guest2));

        when(guestRepository.findByUser(user)).thenReturn(guests);
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Invitation result = invitationService.createInvitation("You're invited!", "2025-05-25", "http://image.jpg", user);

        assertNotNull(result);
        assertEquals("You're invited!", result.getInvitationMessage());
        assertEquals("2025-05-25", result.getWeddingDate());
        assertEquals("http://image.jpg", result.getInvitationImageUrl());
        assertEquals(user, result.getUser());
        assertEquals(2, result.getGuests().size());

        verify(guestRepository).findByUser(user);
        verify(invitationRepository).save(any(Invitation.class));
    }
}
