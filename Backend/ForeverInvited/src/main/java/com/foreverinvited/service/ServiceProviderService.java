package com.foreverinvited.service;

import com.foreverinvited.model.Service;
import com.foreverinvited.model.User;
import com.foreverinvited.repository.ServiceRepository;
import com.foreverinvited.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Service
public class ServiceProviderService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a new service for the provider (pending approval)
    public Service addService(String userEmail, Service service) {
        User user = userRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        service.setUser(user);
        // service.setStatus("Pending");  // Default status is Pending
        return serviceRepository.save(service);
    }

    // Get all services for a service provider (user)
    public List<Service> getServices(String userEmail) {
        User user = userRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        return serviceRepository.findByUserEmail(user.getEmail());
    }

    // Approve a service
    public Service approveService(Long serviceId) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        // service.setStatus("Approved");
        return serviceRepository.save(service);
    }

    // Reject a service
    public Service rejectService(Long serviceId) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        // ((Service) service).setStatus("Rejected");
        return serviceRepository.save(service);
    }
}
