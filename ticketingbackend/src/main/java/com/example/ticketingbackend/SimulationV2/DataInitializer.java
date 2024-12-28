package com.example.ticketingbackend.SimulationV2;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Entities.Event;
import com.example.ticketingbackend.Entities.Ticket;
import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Logger.Logger;
import com.example.ticketingbackend.Service.EventService;
import com.example.ticketingbackend.Service.VendorEventsService;

import com.example.ticketingbackend.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// The Data Initializer Class Will Be Used To Initialize the Event. Vendors Involved. The Assigned No Of Tickets Each.
// Making sure the Simulation V2 will run with the same parameters.

// The event will be loaded using eventService loadEvent method. A controller will get Event ID
// The Vendor Details will be fetched here
@Service

public class DataInitializer {
    /*   {"noOfTickets": 60, "maxTicketCapacity": 15, "ticketRate": 5000.0, "releaseRate": 10,
        "retrievalRate": 11, "noOfVendors": 2,  "assignedNoOfTickets": [40, 20]}  */
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private VendorEventsService vendorEventsService;
    private Configuration config;
    private Event event;
    private int eventId;
    private Logger logger;
    @Autowired
    private VendorService vendorService;

    // initialize Configurations
    public void IntializeConfiguration(){
        try {
            Logger.log("Configuration Loaded Successfully");
            this.config = configurationService.loadConfiguration();
        } catch (IOException e) {
            Logger.faultLog(Thread.currentThread().getName(), String.valueOf(e));
            throw new RuntimeException("Failed to Load Configuration While Initializing Data for Simulation "+e);

        }
    }

    // Load the event
    public void InitializeEvent(int eventId){
        try {
            this.event = eventService.loadEvent(eventId);
        }catch (Exception e){
            Logger.faultLog(Thread.currentThread().getName(),String.valueOf(e));
            throw new RuntimeException("Failed to Load Event While Initializing Data for Simulation \"+e");
        }
    }
    // Reusable method to ensure the event is initialized
    private void ensureEventInitialized() {
        if (event == null) {
            String errorMessage = "Event not initialized. Call initializeEvent() first.";
            Logger.faultLog("IllegalStateException", errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    // Reusable method to ensure the configuration is initialized
    private void ensureConfigurationInitialized() {
        if (config == null) {
            String errorMessage = "Configuration not initialized. Call initializeConfiguration() first.";
            Logger.faultLog("IllegalStateException", errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    public int RetrievalRate(){
        ensureConfigurationInitialized();
        return config.getRetrievalRate();
    }
    public int MaxTicketCapacity(){
        ensureConfigurationInitialized();
        return config.getMaxTicketCapacity();
    }
    public int ReleaseRate(){
        ensureConfigurationInitialized();
        return config.getReleaseRate();
    }

    public double TicketRate(){
        ensureConfigurationInitialized();
        return config.getTicketRate();
    }
    public int NoOfTickets(){
        ensureEventInitialized();
        return event.getNoOfTickets();
    }
    public int NoOfVendors(){
        ensureEventInitialized();
        return event.getNoOfVendors();
    }
    public List<Integer> AssignedVendors(){
        try {
            ensureEventInitialized();
            return vendorEventsService.getVendorIdsForEvent(eventId);
        }catch (Exception e){
            String errorMessage = "Vendors haven't been Assigned To this Event. Please Assign Vendors first";
            Logger.faultLog(Thread.currentThread().getName(), errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }
    public Map<Integer, Integer> VendorsAndAssignedTickets(int eventId) {
        try {
            // Ensure the event is initialized
            ensureEventInitialized();

            // Fetch the list of vendor IDs for the event
            List<Integer> vendorIds = vendorEventsService.getVendorIdsForEvent(eventId);

            // Map to hold vendorId -> assignedTickets
            Map<Integer, Integer> vendorTicketsMap = new HashMap<>();

            // Populate the map with vendor IDs and assigned tickets
            for (Integer vendorId : vendorIds) {
                try {
                    int assignedTickets = vendorEventsService.getAssignedNoOfTickets(vendorId, eventId);
                    vendorTicketsMap.put(vendorId, assignedTickets);
                } catch (Exception e) {
                    String errorMessage = "Error fetching assigned tickets for Vendor ID: " + vendorId;
                    Logger.faultLog(Thread.currentThread().getName(), errorMessage + " - " + e.getMessage());
                    // Optionally, skip the vendor or add -1 to indicate an error
                    vendorTicketsMap.put(vendorId, -1); // Using -1 to indicate error
                }
            }

            return vendorTicketsMap;

        } catch (Exception e) {
            String errorMessage = "Failed to fetch vendors and assigned tickets for Event ID: " + eventId;
            Logger.faultLog(Thread.currentThread().getName(), errorMessage + " - " + e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        }
    }


    public String getEventName() {
       ensureEventInitialized();
        return event.getEventName();
    }
    public Event getEvent() {
        ensureEventInitialized();
        return event;
    }

    // Generate A Ticket
    public Ticket generateTicket(Vendor vendor, Event event) {
        try {
            // Generate a new ticket
            int ticketId = TicketIdGenerator.getNextTicketId();
            String ticketName = "Ticket-" + ticketId;
            String availability = "Available";

            // Create the ticket
            Ticket ticket = new Ticket();
            ticket.setTicketId(ticketId); // Optional if @GeneratedValue is used
            ticket.setTicketName(ticketName);
            ticket.setTicketRate(TicketRate());
            ticket.setAvailability(availability);
            ticket.setVendors(vendor);
            ticket.setEvent(event);

            System.out.println("Generated Ticket: " + ticketName);
            Logger.log("Generated Ticket: " + ticketName);

            return ticket;

        } catch (Exception e) {
            String errorMessage = "Error generating ticket for event: " + event.getEventName();
            Logger.faultLog(Thread.currentThread().getName(), errorMessage + " - " + e.getMessage());
            throw new RuntimeException(errorMessage, e);
        }
    }
    public Vendor getVendorById(int vendorId) {
        try {
            // Fetch the vendor by ID
            return vendorService.getVendor(vendorId)
                    .orElseThrow(() -> new IllegalArgumentException("Vendor with ID " + vendorId + " not found."));
        } catch (Exception e) {
            // Log and handle exceptions
            Logger.faultLog(Thread.currentThread().getName(), "Error fetching Vendor with ID: " + vendorId + " - " + e.getMessage());
            throw new RuntimeException("Error fetching Vendor with ID " + vendorId, e);
        }
    }



}

