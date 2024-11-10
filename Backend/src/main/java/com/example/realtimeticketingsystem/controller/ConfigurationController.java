package com.example.realtimeticketingsystem.controller;

import com.example.realtimeticketingsystem.service.ConfigurationService;
import com.example.realtimeticketingsystem.entity.ConfigurationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
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
}
