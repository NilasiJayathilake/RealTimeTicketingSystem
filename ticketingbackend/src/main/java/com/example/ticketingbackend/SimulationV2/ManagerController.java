package com.example.ticketingbackend.SimulationV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulation2")
public class ManagerController {
    private final Manager manager;
    @Autowired
    public ManagerController(Manager manager){
        this.manager=manager;
    }
    @PostMapping("/start/{eventId}")
    public ResponseEntity<String> startManagement(
            @PathVariable int eventId) {
        try {
            manager.startManagement(eventId);
            return ResponseEntity.ok("Management system started for eventId: " + eventId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error starting management system: " + e.getMessage());
        }
    }

    @PostMapping("/shutdown")
    public ResponseEntity<String> shutdownExecutors() {
        try {
            manager.shutdownExecutors();
            return ResponseEntity.ok("Executors shut down successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error shutting down executors: " + e.getMessage());
        }
    }


}
