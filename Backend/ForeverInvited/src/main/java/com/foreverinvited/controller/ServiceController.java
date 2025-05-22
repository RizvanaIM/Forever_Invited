package com.foreverinvited.controller;

import com.foreverinvited.model.Service;
import com.foreverinvited.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*") 
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    // Get all services
    @GetMapping
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    // Add a new service
    @PostMapping("/add")
    public Service addService(@RequestBody Service service) {
        return serviceService.addService(service);
    }

    // Edit an existing service
    @PutMapping("/update/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service service) {
        return serviceService.updateService(id, service);
    }

    // Delete a service
    @DeleteMapping("/delete/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
