package com.example.ticketingbackend.SimulationV2;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Entities.Ticket;
import com.example.ticketingbackend.Logger.Logger;
import com.example.ticketingbackend.Service.EventService;
import com.example.ticketingbackend.Service.VendorEventsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

@Service
@Getter
public class Ticketpool {
    // Using a Concurrent Linked Queue which is thread safe, and also dynamic
    private Queue<Ticket> tickets;
    @Autowired
    private ConfigurationService config;
    @Autowired
    private EventService event;
    @Autowired
    private VendorEventsService vendorEventsService;
    @Autowired
    private DataInitializer dataInitializer;

    public Ticketpool() {
        this.tickets = new LinkedList<>();
    }
    public synchronized void addTicket(Ticket ticket, int eventId) {
        try {
            // Wait if the ticket pool is at maximum capacity
            while (tickets.size() >= config.loadConfiguration().getMaxTicketCapacity()) {
                System.out.println("Ticket Pool Capacity Reached. Waiting to release more tickets...");
                System.out.println("Current Pool Size: " + tickets.size());
                Logger.log("Ticket Pool Capacity Reached. Waiting to release more tickets...");
                Logger.log("Current Pool Size: " + tickets.size());
                wait(); // Wait till customers buy tickets
            }
            // Add the ticket to the pool
            if (tickets.size()< event.loadEvent(eventId).getNoOfTickets()){
                tickets.add(ticket);
                Logger.log("Ticket " +ticket.getTicketId()+ " Added for "+event.loadEvent(eventId).getEventName());
                System.out.println("Ticket "+ticket.getTicketId()+" Added for "+event.loadEvent(eventId).getEventName());
                notifyAll(); // Notify all waiting threads that a ticket was added
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted while waiting to add a ticket: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Removing a Ticket from the Pool
        Called by the Customer
     */
    public synchronized Ticket removeTicket(int eventId){
        try {
            while (tickets.isEmpty()) {
                Logger.log("TicketPool is Empty. Customer is Waiting");
                System.out.println("TicketPool is Empty. Customer is Waiting");
                wait();
            }} catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Ticket ticket = tickets.poll();

        notifyAll(); // to notify all threads tickets have been bought
        System.out.println("Ticket: "+ticket.getTicketId()+" Has Been Purchased for "+event.loadEvent(eventId).getEventName());
        Logger.log("Ticket: "+ticket.getTicketId()+" Has Been Purchased for "+event.loadEvent(eventId).getEventName());
        return ticket;
    }

    public int PoolSize(){
        return tickets.size();
    }

    public double Revenue(){
        return dataInitializer.TicketRate();
    }
}
