package com.foreverinvited.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invitationMessage;
    private String weddingDate;
    private String invitationImageUrl;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;


    @ManyToMany
    @JoinTable(
        name = "invitation_guest",
        joinColumns = @JoinColumn(name = "invitation_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id")
    )

    @JsonManagedReference
    private Set<Guest> guests;

    public Invitation(String message, String date) {
        this.invitationMessage = message;
        this.weddingDate = date;
    }


}
