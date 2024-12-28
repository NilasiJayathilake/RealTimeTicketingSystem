package com.example.ticketingbackend.Controller;

import com.example.ticketingbackend.DTO.StartRequest;
import com.example.ticketingbackend.Entities.Customer;
import com.example.ticketingbackend.Service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ManagementController {
    ManagementService managementService;

    @Autowired
    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSimulation(@RequestBody StartRequest startRequest){
        List<Integer> vendorIds = startRequest.getVendorIds();
        int eventId = startRequest.getEventId();
        List<Customer> customers = startRequest.getCustomers();

        managementService.startOperations(vendorIds, eventId, customers);
        return ResponseEntity.ok("Started vendors and customers.");
    }
}
