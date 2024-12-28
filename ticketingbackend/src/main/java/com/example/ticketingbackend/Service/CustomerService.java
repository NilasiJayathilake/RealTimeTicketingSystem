package com.example.ticketingbackend.Service;
import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Entities.*;
import com.example.ticketingbackend.Repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.ticketingbackend.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private TicketingService ticketingService;
    private SimpMessagingTemplate messagingTemplate;
    private Configuration config;
    private EventRepository eventRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, TicketingService ticketingService, Configuration config, EventRepository eventRepository ) {
        this.customerRepository = customerRepository;
        this.ticketingService = ticketingService;
//        this.messagingTemplate = messagingTemplate;
        this.config=config;
        this.eventRepository=eventRepository;
    }

    // Purchasing Ticket Logic of Customer

    @Transactional
    public Customer buyTicket(Customer customer, int eventId) {
        try {
            while (true) {
                synchronized (ticketingService.getTickets()) {
                    Ticket ticket = ticketingService.removeTicket();

                    if (ticket != null && ticket.getAvailability().equals("available")) {
                        ticket.setAvailability("sold");
                        ticket.setEvent(eventRepository.findById(eventId)
                                .orElseThrow(() -> new RuntimeException("Couldn't Process Event")));

                        customer.setTicket(ticket);

                        Customer savedCustomer = customerRepository.save(customer);

                        System.out.println("Purchased By: " + customer.getCustomerName() +
                                " Executed By: " + Thread.currentThread().getName() +
                                "\n Current Pool Size: " + ticketingService.PoolSize());

//                    messagingTemplate.convertAndSend("/topic/ticket-updates", "Ticket purchased: " + ticket.toString());

                        return savedCustomer; // Return the saved customer
                    }

                    Thread.sleep((10-config.getRetrievalRate()) * 500L);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while purchasing ticket", e);
        }
    }


    // to register a Customer to the system (SignUp)
    public Customer saveCustomer(Customer customer){
        try {
            return customerRepository.save(customer);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error signing up : " + e.getMessage());
        }
    }

}
