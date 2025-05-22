package com.foreverinvited.service;

import com.foreverinvited.model.Service;
import com.foreverinvited.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    // Get all services
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Add a new service
    public Service addService(Service service) {
        return serviceRepository.save(service);
    }

    // Update an existing service
    public Service updateService(Long id, Service service) {
        Optional<Service> existingService = serviceRepository.findById(id);
        if (existingService.isPresent()) {
            service.setId(id);
            return serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found");
        }
    }

    // Delete a service
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
