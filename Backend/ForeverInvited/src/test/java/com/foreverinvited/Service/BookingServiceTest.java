package com.foreverinvited.Service;

import com.foreverinvited.model.*;
import com.foreverinvited.repository.*;
import com.foreverinvited.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into BookingService
    }

    @Test
    void testAddToCart() {
        User user = new User();
        com.foreverinvited.model.Service service = new com.foreverinvited.model.Service();

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        bookingService.addToCart("test@example.com", 1L);

        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void testGetCart() {
        User user = new User();
        com.foreverinvited.model.Service service = new com.foreverinvited.model.Service();
        CartItem item = new CartItem();
        item.setService(service);

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUser(user)).thenReturn(List.of(item));

        List<com.foreverinvited.model.Service> result = bookingService.getCart("test@example.com");

        assertEquals(1, result.size());
        verify(cartItemRepository).findByUser(user);
    }

    @Test
    void testConfirmBooking() {
        User user = new User();
        com.foreverinvited.model.Service service = new com.foreverinvited.model.Service();
        CartItem item = new CartItem();
        item.setUser(user);
        item.setService(service);

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUser(user)).thenReturn(List.of(item));

        bookingService.confirmBooking("test@example.com");

        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(cartItemRepository).deleteByUser(user);
    }

    @Test
    void testGetUserBookings() {
        User user = new User();
        List<Booking> mockBookings = List.of(new Booking());

        when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
        when(bookingRepository.findByUser(user)).thenReturn(mockBookings);

        List<Booking> result = bookingService.getUserBookings("test@example.com");

        assertEquals(1, result.size());
        verify(bookingRepository).findByUser(user);
    }

    @Test
    void testGetBookingsForProvider() {
        List<Booking> mockBookings = List.of(new Booking());

        when(bookingRepository.findBookingsByProviderEmail("provider@example.com")).thenReturn(mockBookings);

        List<Booking> result = bookingService.getBookingsForProvider("provider@example.com");

        assertEquals(1, result.size());
        verify(bookingRepository).findBookingsByProviderEmail("provider@example.com");
    }

    @Test
    void testUpdateProviderStatus() {
        Booking booking = new Booking();
        booking.setProviderStatus("Pending");

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        bookingService.updateProviderStatus(1L, "Accepted");

        assertEquals("Accepted", booking.getProviderStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void testGetBookingsByUserEmail() {
        List<Booking> mockBookings = List.of(new Booking());

        when(bookingRepository.findByUserEmail("user@example.com")).thenReturn(mockBookings);

        List<Booking> result = bookingService.getBookingsByUserEmail("user@example.com");

        assertEquals(1, result.size());
        verify(bookingRepository).findByUserEmail("user@example.com");
    }
}
