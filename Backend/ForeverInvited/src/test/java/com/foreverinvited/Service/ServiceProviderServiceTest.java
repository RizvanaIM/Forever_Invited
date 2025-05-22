package com.foreverinvited.Service;

import com.foreverinvited.model.Service;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.ServiceRepository;
import com.foreverinvited.repository.UserRepository;
import com.foreverinvited.service.ServiceProviderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServiceProviderServiceTest {

    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ServiceProviderService serviceProviderService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        serviceRepository = mock(ServiceRepository.class);

        serviceProviderService = new ServiceProviderService();

        // Inject mocks into the private fields using reflection
        try {
            var userRepoField = ServiceProviderService.class.getDeclaredField("userRepository");
            userRepoField.setAccessible(true);
            userRepoField.set(serviceProviderService, userRepository);

            var serviceRepoField = ServiceProviderService.class.getDeclaredField("serviceRepository");
            serviceRepoField.setAccessible(true);
            serviceRepoField.set(serviceProviderService, serviceRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddService_Success() {
        User user = new User();
        user.setEmail("provider@example.com");

        Service service = new Service();

        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(user));
        when(serviceRepository.save(service)).thenAnswer(invocation -> {
            Service s = invocation.getArgument(0);
            assertEquals(user, s.getUser());
            return s;
        });

        Service result = serviceProviderService.addService("provider@example.com", service);

        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    void testAddService_UserNotFound() {
        Service service = new Service();

        when(userRepository.findById("missing@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceProviderService.addService("missing@example.com", service));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetServices_Success() {
        User user = new User();
        user.setEmail("provider@example.com");

        List<Service> mockServices = List.of(new Service(), new Service());

        when(userRepository.findById("provider@example.com")).thenReturn(Optional.of(user));
        when(serviceRepository.findByUserEmail("provider@example.com")).thenReturn(mockServices);

        List<Service> services = serviceProviderService.getServices("provider@example.com");

        assertEquals(2, services.size());
    }

    @Test
    void testGetServices_UserNotFound() {
        when(userRepository.findById("missing@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceProviderService.getServices("missing@example.com"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testApproveService_Success() {
        Service service = new Service();
        service.setId(1L);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(serviceRepository.save(service)).thenReturn(service);

        Service result = serviceProviderService.approveService(1L);

        assertEquals(service, result);
    }

    @Test
    void testApproveService_NotFound() {
        when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceProviderService.approveService(99L));

        assertEquals("Service not found", exception.getMessage());
    }

    @Test
    void testRejectService_Success() {
        Service service = new Service();
        service.setId(2L);

        when(serviceRepository.findById(2L)).thenReturn(Optional.of(service));
        when(serviceRepository.save(service)).thenReturn(service);

        Service result = serviceProviderService.rejectService(2L);

        assertEquals(service, result);
    }

    @Test
    void testRejectService_NotFound() {
        when(serviceRepository.findById(88L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceProviderService.rejectService(88L));

        assertEquals("Service not found", exception.getMessage());
    }
}
