package com.foreverinvited.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foreverinvited.model.Service;
import com.foreverinvited.service.ServiceProviderService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/provider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    // Endpoint to add a new service for the service provider
    @PostMapping("/add-service")
    public ResponseEntity<String> addService(@RequestParam String userEmail, @RequestBody Service service) {
        serviceProviderService.addService(userEmail, service);
        return ResponseEntity.ok("Service added successfully, awaiting approval.");
    }

    // Endpoint to get all services for a specific service provider
    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices(@RequestParam String userEmail) {
        List<Service> services = serviceProviderService.getServices(userEmail);
        return ResponseEntity.ok(services);
    }

    // Endpoint to approve a service
    @PutMapping("/approve-service/{serviceId}")
    public ResponseEntity<String> approveService(@PathVariable Long serviceId) {
        serviceProviderService.approveService(serviceId);
        return ResponseEntity.ok("Service approved.");
    }

    // Endpoint to reject a service
    @PutMapping("/reject-service/{serviceId}")
    public ResponseEntity<String> rejectService(@PathVariable Long serviceId) {
        serviceProviderService.rejectService(serviceId);
        return ResponseEntity.ok("Service rejected.");
    }
}
