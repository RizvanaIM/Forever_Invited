package com.foreverinvited.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foreverinvited.model.Booking;
import com.foreverinvited.model.User;

public interface BookingRepository extends JpaRepository<Booking, Long>{

    List<Booking> findByUser(User user);

    List<Booking> findByUserEmail(String email);

    @Query("SELECT b FROM Booking b WHERE b.service.user.email = :email")
    List<Booking> findBookingsByProviderEmail(@Param("email") String email);

}
