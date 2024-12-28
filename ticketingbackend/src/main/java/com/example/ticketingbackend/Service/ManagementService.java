package com.example.ticketingbackend.Service;

import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Entities.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Getter
@Setter
public class ManagementService {
    private TicketingService ticketingService;
    private ConfigurationService configurationService;

    private VendorService vendorService;
    private VendorEventsService vendorEventsService;
    private CustomerService customerService;

    // ExecutorServices
    private ExecutorService vendorExecutor = Executors.newFixedThreadPool(10);
    private ExecutorService customerExecutor = Executors.newFixedThreadPool(10);

    @Autowired
    public ManagementService(TicketingService ticketingService, ConfigurationService configurationService, VendorService vendorService, VendorEventsService vendorEventsService, CustomerService customerService) {
        this.ticketingService = ticketingService;
        this.configurationService = configurationService;
        this.vendorService = vendorService;
        this.vendorEventsService = vendorEventsService;
        this.customerService = customerService;
    }

    public void startOperations(List<Integer> vendorIds, int eventId, List<Customer> customers) {
        // Start vendors
        for (int vendorId : vendorIds) {
            vendorExecutor.submit(() -> {
                try {
                    System.out.println("VENDOR");
                    vendorService.addTicket(eventId, vendorId);
                } catch (Exception e) {
                    System.err.println("Error adding tickets for vendor " + vendorId + ": " + e.getMessage());
                }
            });
        }

        // Start customers
        for (Customer customer : customers) {
            customerExecutor.submit(() -> {
                try {
                    System.out.println("CUSTOMER");
                    customerService.buyTicket(customer, eventId);
                    System.out.println("Customer successfully bought a ticket");
                } catch (Exception e) {
                    System.err.println("Error buying ticket for customer " + customer.getCustomerName() + ": " + e.getMessage());
                }
            });
        }
    }
    public void shutdown() {
        vendorExecutor.shutdown();
        try {
            if (!vendorExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                vendorExecutor.shutdownNow();
                System.out.println("Vendor Has Shutdown");
            }
        customerExecutor.shutdown();
            if (!customerExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                customerExecutor.shutdownNow();
                System.out.println("Customer has Shutdown");
            }
        } catch (InterruptedException e) {
            vendorExecutor.shutdownNow();
            customerExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

//    // Method to add tasks for vendors
//    public void startAddingTickets(List<Integer> vendorIds, int eventId, VendorService vendorService) {
//        for (int vendorId : vendorIds) {
//            vendorExecutor = Executors.newFixedThreadPool(vendorIds.size());
//            vendorExecutor.submit(() -> {
//                try {
//                    vendorService.addTicket(eventId, vendorId);
//                } catch (Exception e) {
//                    System.err.println("Error adding tickets for vendor " + vendorId + ": " + e.getMessage());
//                }
//            });
//        }
//    }
//
//
//    public void startCustomersBuyingTickets(List<Customer> customers, int eventId, CustomerService customerService) {
//        for (Customer customer : customers) {
//            customerExecutor.submit(() -> {
//                try {
//                    Customer result = customerService.buyTicket(customer, eventId);
//                    System.out.println("Customer successfully bought a ticket: " + result);
//                } catch (Exception e) {
//                    System.err.println("Error buying ticket for customer " + customer.getCustomerName() + ": " + e.getMessage());
//                }
//            });
//        }
//    }

}
