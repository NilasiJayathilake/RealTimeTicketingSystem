package com.example.ticketingbackend.Controller;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Logger.Logger;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
@CrossOrigin(origins = "http://localhost:3000")
public class ConfigurationController {
    ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping("/saveConfig")
    public String addConfiguration(@RequestBody Configuration configuration){
        try {
            configurationService.setConfiguration(configuration);
            return configurationService.saveConfiguration();
        }catch (IOException e){
            throw new RuntimeException("Couldn't save Configurations"+e.getMessage());
        }
    }

    // this method will be just used to view the configs mostly
    @GetMapping("/loadConfig")
    public Configuration loadConfiguration(){
        try {
           // System.out.println("Max Ticket Capacity: " +configurationService.loadConfiguration().getMaxTicketCapacity());
            return configurationService.loadConfiguration();
        }catch (IOException e){
            throw new RuntimeException("Error Loading Config from the File"+e.getMessage());
        }
    }

    @GetMapping("/viewConfig")
    @ResponseBody
    public Map<String, Object> viewConfiguration() {
        try {
            Configuration config = configurationService.loadConfiguration();
            Map<String, Object> configMap = new HashMap<>();
            configMap.put("maxTicketCapacity", config.getMaxTicketCapacity());
            configMap.put("ticketRate", config.getTicketRate());
            configMap.put("releaseRate", config.getReleaseRate());
            configMap.put("retrievalRate", config.getRetrievalRate());
            return configMap;
        } catch (IOException e) {
            throw new RuntimeException("Error Loading Config from the File" + e.getMessage());
        }
    }


}
