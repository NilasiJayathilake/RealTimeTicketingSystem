package com.example.realtimeticketingsystem.controller;

import com.example.realtimeticketingsystem.service.ConfigurationService;
import com.example.realtimeticketingsystem.entity.ConfigurationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {
    private ConfigurationService service;

    @Autowired
    public ConfigurationController(ConfigurationService service) {
        this.service = service;
    }

    @PostMapping("/addConfiguration")
    public ConfigurationEntity addConfiguration(@RequestBody ConfigurationEntity configuration){
        try {
           return service.saveConfiguration(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/file")
    public void FileExistence(){
        service.readFile();
    }
    @GetMapping("/Events")
    public List<String> getAllEventNames(){
        return service.retrieveEventNames();
    }
    @GetMapping("/NoOfTickets")
    public List<Integer> getAllNoOfTickets(){
        return service.retrieveNoOfTickets();
    }
    @GetMapping("/TicketRates")
    public List<Double> getAllTicketRates(){
        return service.retrieveTicketRates();
    }
}
