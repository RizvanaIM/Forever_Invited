package com.foreverinvited.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreverinvited.controller.ServiceProviderController;
import com.foreverinvited.model.Service;
import com.foreverinvited.service.ServiceProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceProviderController.class)
class ServiceProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceProviderService serviceProviderService;

    private ObjectMapper objectMapper;
    private Service testService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        testService = new Service();
        testService.setId(1L);
        testService.setServiceType("Photography");
        testService.setDescription("Wedding photography");
        testService.setNameOfPhotographer("Photography");
        testService.setPrice(1000.0);
    }

    @Test
    void testAddService() throws Exception {
        mockMvc.perform(post("/api/provider/add-service")
                        .param("userEmail", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testService)))
                .andExpect(status().isOk())
                .andExpect(content().string("Service added successfully, awaiting approval."));

        Mockito.verify(serviceProviderService).addService(eq("test@example.com"), any(Service.class));
    }

    @Test
    void testGetServices() throws Exception {
        Mockito.when(serviceProviderService.getServices("test@example.com")).thenReturn(List.of(testService));

        mockMvc.perform(get("/api/provider/services")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceType").value("Photography"));

        Mockito.verify(serviceProviderService).getServices("test@example.com");
    }

    @Test
    void testApproveService() throws Exception {
        mockMvc.perform(put("/api/provider/approve-service/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service approved."));

        Mockito.verify(serviceProviderService).approveService(1L);
    }

    @Test
    void testRejectService() throws Exception {
        mockMvc.perform(put("/api/provider/reject-service/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service rejected."));

        Mockito.verify(serviceProviderService).rejectService(1L);
    }
}
