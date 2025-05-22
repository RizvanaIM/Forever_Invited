package com.foreverinvited.Service;

import com.foreverinvited.model.Service;
import com.foreverinvited.repository.ServiceRepository;
import com.foreverinvited.service.ServiceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceServiceTest {

    private ServiceRepository serviceRepository;
    private ServiceService serviceService;

    @BeforeEach
    void setUp() {
        serviceRepository = mock(ServiceRepository.class);

        // Create a subclass to access private field via injection workaround
        serviceService = new ServiceService() {
            {
                // Inject mock manually into the private field
                try {
                    var field = ServiceService.class.getDeclaredField("serviceRepository");
                    field.setAccessible(true);
                    field.set(this, serviceRepository);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void testGetAllServices() {
        Service s1 = new Service();
        Service s2 = new Service();

        when(serviceRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Service> services = serviceService.getAllServices();
        assertEquals(2, services.size());
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void testAddService() {
        Service service = new Service();
        service.setServiceType("Photography");

        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        Service saved = serviceService.addService(service);
        assertEquals("Photography", saved.getServiceType());
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void testUpdateService_Success() {
        Long id = 1L;

        Service existing = new Service();
        existing.setId(id);
        existing.setServiceType("Old Name");

        Service updated = new Service();
        updated.setServiceType("New Name");

        when(serviceRepository.findById(id)).thenReturn(Optional.of(existing));
        when(serviceRepository.save(any(Service.class))).thenReturn(updated);

        Service result = serviceService.updateService(id, updated);
        assertEquals("New Name", result.getServiceType());

        verify(serviceRepository, times(1)).findById(id);
        verify(serviceRepository, times(1)).save(updated);
    }

    @Test
    void testUpdateService_NotFound() {
        Long id = 1L;
        Service updated = new Service();
        updated.setServiceType("New Name");

        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceService.updateService(id, updated);
        });

        assertEquals("Service not found", exception.getMessage());
        verify(serviceRepository, times(1)).findById(id);
        verify(serviceRepository, never()).save(any());
    }

    @Test
    void testDeleteService() {
        Long id = 1L;

        doNothing().when(serviceRepository).deleteById(id);

        serviceService.deleteService(id);
        verify(serviceRepository, times(1)).deleteById(id);
    }
}
