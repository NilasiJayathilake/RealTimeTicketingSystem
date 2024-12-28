package com.example.ticketingbackend.Service;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Entities.*;
import com.example.ticketingbackend.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private TicketingService ticketingService;
    private VendorEventsService vendorEventsService;
    ConfigurationService configurationService;
    EventService eventService;
    private AtomicInteger ticketIdCounter= new AtomicInteger(0);
   ExecutorService vendorExecutor;

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketingService ticketingService, EventService eventService, ConfigurationService configurationService, VendorEventsService vendorEventsService){
        this.vendorRepository=vendorRepository;
        this.ticketingService=ticketingService;
        this.eventService = eventService;
        this.configurationService = configurationService;
        this.vendorEventsService = vendorEventsService;
//        this.messagingTemplate=messagingTemplate;

    }

   @Async
    public Vendor addTicket(int eventId, int vendorId) throws IOException {
        // Threads Handle Releasing tickets to the Pool
       /* if the amount of tickets less is lesser than the release rate, it will add the remaining amount to the pool
            Or else it will add the release rate to the pool*/
        Event event = eventService.loadEvent(eventId);
        Vendor vendor = vendorRepository.findById(vendorId)
               .orElseThrow(() -> new RuntimeException("Couldn't find vendor"));
        int assignedNoOfTickets = vendorEventsService.getAssignedNoOfTickets(vendorId, eventId);

       Configuration config = configurationService.loadConfiguration();
        int count=0;
        synchronized (ticketingService.getTickets()) {

            for (int i = 0; i < assignedNoOfTickets; i++) {
                Ticket ticket = new Ticket();

                String ticketName = generateTicketName(event.getEventId(), vendor.getVendorId(), ticket.getTicketId() );

                ticket.setTicketId(ticketIdCounter.incrementAndGet());
                ticket.setTicketName(ticketName);
                ticket.setTicketRate(config.getTicketRate());
                ticket.setAvailability("available");

                ticketingService.addTicket(ticket);
                count++;
                System.out.println("Vendor: " + Thread.currentThread().getName() + "Added Ticket No: " + ticket.getTicketId()
                        + "\n Pool Size Currently: " + ticketingService.PoolSize());
//                messagingTemplate.convertAndSend("/topic/ticket-updates", "Vendor added ticket: " + ticket.toString());
            }

        }

        try {
            Thread.sleep(config.getReleaseRate()* 500L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Vendor "+vendorId+" Finished Adding Tickets.\n" +
                " Executed by: "+Thread.currentThread().getName()+" Added Count: "+count);

       return vendor;
   }
    // to add a Vendor to the system
    public Vendor saveVendor(Vendor vendor){
        try {
            return vendorRepository.save(vendor);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error saving Vendor: " + e.getMessage());
        }
    }

    // To add Multiple Vendors
    public List<Vendor> saveVendors(List<Vendor> vendors){
        try {
            return vendorRepository.saveAll(vendors);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error saving Vendors List: " + e.getMessage());
        }
    }

    public List<Vendor> getVendors(){
        return vendorRepository.findAll();
    }

    private String generateTicketName(int eventId, int vendorId, int ticketId) {
        return String.format("T-E%d-V%d-%d", eventId, vendorId, ticketId);
    }

    public Optional<Vendor> getVendor(int vendorId){
        return vendorRepository.findById(vendorId);
    }


}
