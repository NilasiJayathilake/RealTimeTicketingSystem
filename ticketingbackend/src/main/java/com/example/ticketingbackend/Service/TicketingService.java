package com.example.ticketingbackend.Service;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Entities.Ticket;
import com.example.ticketingbackend.Repository.TicketingRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

// ticket pool is not considered as an entity. As it handles the business logic of the program it is a service.
@Service
@Getter
@Setter
public class TicketingService {
    Logger logger;

    ConfigurationService configurationService;
    private Queue<Ticket> tickets;
    private TicketingRepository ticketingRepository;
    private AtomicInteger ticketIdCounter= new AtomicInteger(0);
    Configuration config;

    @Autowired
    public TicketingService(ConfigurationService configurationService, TicketingRepository ticketingRepository) throws IOException {
        this.configurationService = configurationService;
        this.ticketingRepository=ticketingRepository;

        Configuration config = configurationService.loadConfiguration();

    }

    @PostConstruct
    public void init()throws IOException{
        this.config=configurationService.loadConfiguration();

        this.tickets = new LinkedBlockingQueue<>(config.getMaxTicketCapacity());
        System.out.println("Initialized TicketingService with capacity: " + config.getMaxTicketCapacity());
    }

    // will be used by the Vendor Service

    public synchronized void addTicket(Ticket ticket) {
        try {
            // Wait if the ticket pool is at maximum capacity
            while (tickets.size() == config.getMaxTicketCapacity()) {
               logger.info("Ticket Pool Capacity Reached. Waiting to release more tickets...");
               logger.info("Current Pool Size: " + tickets.size());
                wait(); // Wait till customers buy tickets
            }
            // Add the ticket to the pool
            tickets.add(ticket);
            notifyAll(); // Notify all waiting threads that a ticket was added
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.debug("Thread interrupted while waiting to add a ticket: " + e.getMessage());
        }
    }

    // will be used by the Customer Service
    public synchronized Ticket removeTicket(){
        try {
            while (tickets.isEmpty()) {
                wait();
                logger.info("Customer Waiting. Ticket Pool empty");
            }} catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Ticket ticket = tickets.poll();

        notifyAll(); // to notify all threads tickets have been bought
        logger.info(+ticket.getTicketId()+"Has Been Purchased");
        return ticket;
    }
    public int PoolSize(){
        return tickets.size();
    }


}
