package com.foreverinvited.Controller;


import com.foreverinvited.controller.BookingController;
import com.foreverinvited.model.Booking;
import com.foreverinvited.model.Service;
import com.foreverinvited.model.User;
import com.foreverinvited.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private Booking booking;
    private User user;
    private Service service;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("user@example.com");
        user.setName("Test User");

        service = new Service();
        service.setId(10L);
        service.setServiceType("Photography");

        booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setService(service);
        booking.setBookedDate(LocalDate.now());
        booking.setStatus("Pending");
        booking.setProviderStatus("Awaiting");
    }

    @Test
    void testGetBookingsByEmail() throws Exception {
        Mockito.when(bookingService.getBookingsByUserEmail("user@example.com"))
                .thenReturn(List.of(booking));

        mockMvc.perform(get("/api/services/bookedServices")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.email").value("user@example.com"))
                .andExpect(jsonPath("$[0].service.serviceType").value("Photography"));
    }

    @Test
    void testGetCart() throws Exception {
        Mockito.when(bookingService.getCart("user@example.com"))
                .thenReturn(List.of(service));

        mockMvc.perform(get("/api/services/cart")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceType").value("Photography"));
    }

    @Test
    void testAddToCart() throws Exception {
        mockMvc.perform(post("/api/services/cart/add")
                        .param("email", "user@example.com")
                        .param("id", "10"))
                .andExpect(status().isOk());

        Mockito.verify(bookingService).addToCart("user@example.com", 10L);
    }

    @Test
    void testConfirmBooking() throws Exception {
        mockMvc.perform(post("/api/services/confirm-booking")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk());

        Mockito.verify(bookingService).confirmBooking("user@example.com");
    }

    @Test
    void testGetProviderBookings() throws Exception {
        Mockito.when(bookingService.getBookingsForProvider("provider@example.com"))
                .thenReturn(List.of(booking));

        mockMvc.perform(get("/api/services/provider-bookings")
                        .param("email", "provider@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.email").value("user@example.com"))
                .andExpect(jsonPath("$[0].service.serviceType").value("Photography"));
    }

    @Test
    void testUpdateBookingStatus() throws Exception {
        mockMvc.perform(post("/api/services/provider-bookings/update")
                        .param("bookingId", "1")
                        .param("status", "Confirmed"))
                .andExpect(status().isOk());

        Mockito.verify(bookingService).updateProviderStatus(1L, "Confirmed");
    }
}
