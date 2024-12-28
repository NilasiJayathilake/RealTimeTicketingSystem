package com.example.ticketingbackend.Service;

import com.example.ticketingbackend.Entities.Event;
import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Entities.VendorEvents;
import com.example.ticketingbackend.Repository.EventRepository;
import com.example.ticketingbackend.Repository.VendorEventsRepository;
import com.example.ticketingbackend.Repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//service to add a new event or to load an event
@Service
public class EventService {

    private EventRepository eventRepository;
    private VendorRepository vendorRepository;

    private VendorEventsRepository vendorEventsRepository;

    @Autowired
    public EventService(EventRepository eventRepository, VendorRepository vendorRepository, VendorEventsRepository vendorEventsRepository) {
        this.eventRepository = eventRepository;
        this.vendorRepository = vendorRepository;
        this.vendorEventsRepository = vendorEventsRepository;
    }

    public Event saveEvent(Event event) {
        try {
            return eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving event: " + e.getMessage());
        }
    }
    public Event loadEvent(int id){
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> getLatestEvents(int limit) {
        return eventRepository.findLatestEvents(PageRequest.of(0, limit));
    }


    //assign vendors to an event
    public Event assignVendorService(int event_id, Map<Integer, Integer> vendorTicketsMap){
        Event event = eventRepository.findById(event_id).orElseThrow(()->new RuntimeException("Error Finding the Event"));

        List<Integer> vendorIds = new ArrayList<>(vendorTicketsMap.keySet());
        List<Vendor> vendors = vendorRepository.findAllByVendorIdIn(vendorIds);

        // validating vendors
        if (vendors.size() != vendorIds.size()) {
            try {
                throw new Exception("One or more vendors do not exist. Please Check the Vendor IDs again");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // validating the assigned no of tickets
        int totalNoOfTickets = vendorTicketsMap.values().stream().mapToInt(Integer::intValue).sum();
        if (totalNoOfTickets != event.getNoOfTickets()){
            try{
                throw new Exception("\nTotal No Of Tickets For the event: "+event.getNoOfTickets()+"\n" +
                        "Assigned No Of Tickets: "+totalNoOfTickets+"\n There's a Mismatch. Please check again");
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        for (int j =0; j<vendors.size(); j++){
            Vendor vendor = vendors.get(j);
            Integer assignedTickets = vendorTicketsMap.get(vendor.getVendorId());


            VendorEvents vendorEvents = new VendorEvents();
            vendorEvents.setEvent(event);
            vendorEvents.setVendor(vendor);
            vendorEvents.setAssignedNoOfTickets(assignedTickets);
            event.getVendorEvents().add(vendorEvents);
        }




        return eventRepository.save(event) ;
    }


}
