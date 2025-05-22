package com.foreverinvited.service;

import com.foreverinvited.model.*;
import com.foreverinvited.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ServiceRepository serviceRepository;
    @Autowired private UserRepository userRepository;

    public void addToCart(String email, Long serviceId) {
        User user = userRepository.findById(email).orElseThrow();
        com.foreverinvited.model.Service service = serviceRepository.findById(serviceId).orElseThrow();

        CartItem item = new CartItem();
        item.setUser(user);
        item.setService(service);
        cartItemRepository.save(item);
    }

    public List<com.foreverinvited.model.Service> getCart(String email) {
        User user = userRepository.findById(email).orElseThrow();
        return cartItemRepository.findByUser(user).stream().map(CartItem::getService).toList();
    }

    @Transactional
    public void confirmBooking(String email) {
        User user = userRepository.findById(email).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        for (CartItem item : cartItems) {
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setService(item.getService());
            booking.setBookedDate(LocalDate.now());
            booking.setStatus("Confirmed");
            bookingRepository.save(booking);
        }

        cartItemRepository.deleteByUser(user); // clear cart
    }

    public List<Booking> getUserBookings(String email) {
        User user = userRepository.findById(email).orElseThrow();
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsForProvider(String email) {
    return bookingRepository.findBookingsByProviderEmail(email);
}


    @Transactional
    public void updateProviderStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setProviderStatus(status); // e.g., "Accepted", "Rejected"
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUserEmail(String email) {
    return bookingRepository.findByUserEmail(email);
}

}
