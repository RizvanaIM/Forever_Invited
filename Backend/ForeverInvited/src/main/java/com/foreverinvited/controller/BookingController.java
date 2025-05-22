package com.foreverinvited.controller;

import com.foreverinvited.model.Booking;
import com.foreverinvited.model.Service;
import com.foreverinvited.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired 
    private BookingService bookingService;

    @GetMapping("/bookedServices")
    public List<Booking> getBookingsByEmail(@RequestParam String email) {
        return bookingService.getBookingsByUserEmail(email);
    }

    @GetMapping("/cart")
    public List<Service> getCart(@RequestParam String email) {
        return bookingService.getCart(email);
    }

    @PostMapping("/cart/add")
    public void addToCart(@RequestParam String email, @RequestParam Long id) {
        bookingService.addToCart(email, id);
    }

    @PostMapping("/confirm-booking")
    public void confirmBooking(@RequestParam String email) {
        bookingService.confirmBooking(email);
    }

    @GetMapping("/provider-bookings")
    public List<Booking> getProviderBookings(@RequestParam String email) {
        return bookingService.getBookingsForProvider(email);
    }

    

    @PostMapping("/provider-bookings/update")
    public void updateBookingStatus(@RequestParam Long bookingId, @RequestParam String status) {
        bookingService.updateProviderStatus(bookingId, status);
    }

}
