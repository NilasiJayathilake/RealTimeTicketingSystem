package com.example.ticketingbackend.Controller;

import com.example.ticketingbackend.DTO.EventRequest;
import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendors")
@CrossOrigin(origins = "http://localhost:3000")
public class VendorController {
    VendorService vendorService;
    ConfigurationService configurationService;
    @Autowired
    public VendorController(VendorService vendorService, ConfigurationService configurationService) {
        this.vendorService = vendorService;
        this.configurationService = configurationService;
    }

    @PostMapping("/addVendor")
    public Vendor addVendor(@RequestBody Vendor vendor){
       return vendorService.saveVendor(vendor);
    }

    @PostMapping("/addVendors")
    public List<Vendor> addVendors(@RequestBody List<Vendor> vendors){
        return vendorService.saveVendors(vendors);
    }

    @GetMapping("/testConfigs")
    public Configuration testConfigs(){
        try {
            // System.out.println("Max Ticket Capacity: " +configurationService.loadConfiguration().getMaxTicketCapacity());
            return configurationService.loadConfiguration();
        }catch (IOException e){
            throw new RuntimeException("Error Loading Config from the File"+e.getMessage());
        }

    }
    @PostMapping("/addTicket/{vendorId}")
    public Vendor addTicket(@PathVariable int vendorId, @RequestBody EventRequest eventRequest){
        try{
            return vendorService.addTicket(eventRequest.getEventId(), vendorId);
        }catch (IOException e){
            throw new RuntimeException("Couldn't add it");
        }
    }
    @GetMapping("/viewVendors")
    public List<Vendor> viewVendors(){
        return vendorService.getVendors();
    }
    @GetMapping("/findVendor/{vendorId}")
    public Optional<Vendor> findVendor(@PathVariable int vendorId){
        return vendorService.getVendor(vendorId);
    }


}
