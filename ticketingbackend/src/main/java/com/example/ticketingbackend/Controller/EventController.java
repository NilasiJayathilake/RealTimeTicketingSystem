package com.example.ticketingbackend.Controller;

import com.example.ticketingbackend.Entities.Event;
import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    private EventService eventService;

    // to add an event
    @PostMapping("/addEvent")
    private void addEvent(@RequestBody Event event){
        eventService.saveEvent(event);
        System.out.println("Added Successfully");

    }
    // to load an event already saved
    @GetMapping("/loadEvent/{id}")
    private Event loadEvent(@PathVariable int id){
        return eventService.loadEvent(id);
    }

    // to assign vendors to an Event
    @PostMapping("/{event_id}/assignVendors")
    public Event assignVendors(@PathVariable int event_id, @RequestBody Map<Integer, Integer> vendorTicketsMap){
        return eventService.assignVendorService(event_id, vendorTicketsMap);

    }
    @GetMapping("/test")
    public String test() {
        return "Test successful!";
    }

    @GetMapping("/latestEvents")
    public List<Event> getLatestEvents(@RequestParam(defaultValue = "6") int limit) {
        return eventService.getLatestEvents(limit);
    }
   ;



}
