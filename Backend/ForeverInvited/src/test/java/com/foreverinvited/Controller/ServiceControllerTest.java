package com.foreverinvited.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreverinvited.controller.ServiceController;
import com.foreverinvited.model.Service;
import com.foreverinvited.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Service testService;

    @BeforeEach
    void setUp() {
        testService = new Service();
        testService.setId(1L);
        testService.setServiceType("Photography");
        testService.setDescription("Wedding photography service");
        testService.setPrice(1500.0);
        testService.setNameOfPhotographer("Photography");
    }

    @Test
    void testGetAllServices() throws Exception {
        Mockito.when(serviceService.getAllServices()).thenReturn(List.of(testService));

        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceType").value("Photography"));
    }

    @Test
    void testAddService() throws Exception {
        Mockito.when(serviceService.addService(any(Service.class))).thenReturn(testService);

        mockMvc.perform(post("/api/services/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testService)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceType").value("Photography"));
    }

    @Test
    void testUpdateService() throws Exception {
        Mockito.when(serviceService.updateService(eq(1L), any(Service.class))).thenReturn(testService);

        mockMvc.perform(put("/api/services/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testService)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceType").value("Photography"));
    }

    @Test
    void testDeleteService() throws Exception {
        mockMvc.perform(delete("/api/services/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(serviceService).deleteService(1L);
    }
}
