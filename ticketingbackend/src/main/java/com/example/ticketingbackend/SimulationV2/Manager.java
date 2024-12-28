package com.example.ticketingbackend.SimulationV2;

import com.example.ticketingbackend.Logger.Logger;
import com.example.ticketingbackend.Service.EventService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class Manager {
    private final Ticketpool ticketpool;
    private final DataInitializer dataInitializer;
    private EventService event;
    private ExecutorService VendorExecutorV2;
    private ExecutorService CustomerExecutorV2;

    public Manager(Ticketpool ticketpool, DataInitializer dataInitializer) {
        this.ticketpool = ticketpool;
        this.dataInitializer = dataInitializer;
    }

    public void startManagement(int eventId) {
        try {
            dataInitializer.InitializeEvent(eventId);
            Logger.log("Ticketing For "+dataInitializer.getEventName()+" Has Started");
            dataInitializer.IntializeConfiguration();

            Map<Integer, Integer> VendorTickets = dataInitializer.VendorsAndAssignedTickets(eventId);
            // the no of threads is similar to the amount of vendors
            VendorExecutorV2 = Executors.newFixedThreadPool(dataInitializer.NoOfVendors());
            // Iterates through the map and gets the assignedTickets
            VendorTickets.forEach((vendorId, assignedTickets) -> {
                // initialize the vendor instance
                VendorV2 vendorv2 = new VendorV2(ticketpool, dataInitializer, eventId, vendorId);
                // start submitting vendor
                VendorExecutorV2.submit(vendorv2);
                Logger.log("Vendor " + vendorId + " Submitted");
                System.out.println("Vendor " + vendorId + " Submitted");

                // Using the retrieval rate as the no Of Customers
                int noOfCustomers = dataInitializer.RetrievalRate()*5;
                CustomerExecutorV2 = Executors.newFixedThreadPool(noOfCustomers);
                for (int i = 0; i < noOfCustomers; i++) {
                    // initialize customer instance
                    CustomerV2 customerv2 = new CustomerV2(ticketpool, dataInitializer, eventId,i);
                    // submit customer
                    CustomerExecutorV2.submit(customerv2);
                    Logger.log("Customer Submitted");
                }
            });

        } catch (Exception e) {
            Logger.faultLog(Thread.currentThread().getName(), "Error occurred while starting executors " + e.getMessage());
        }
    }

    public void shutdownExecutors() {
        try {
            System.out.println("Shutting down executors...");
            Logger.log("Shutting down executors...");

            if (VendorExecutorV2 != null) {
                VendorExecutorV2.shutdown(); // Initiates an orderly shutdown
                if (!VendorExecutorV2.awaitTermination(10, TimeUnit.SECONDS)) {
                    VendorExecutorV2.shutdownNow(); // Forces shutdown if tasks do not terminate
                    Logger.log("VendorExecutorV2 forced shutdown.");
                }
            }

            if (CustomerExecutorV2 != null) {
                CustomerExecutorV2.shutdown(); // Initiates an orderly shutdown
                if (!CustomerExecutorV2.awaitTermination(10, TimeUnit.SECONDS)) {
                    CustomerExecutorV2.shutdownNow(); // Forces shutdown if tasks do not terminate
                    Logger.log("CustomerExecutorV2 forced shutdown.");
                }
            }

            System.out.println("Executors shut down successfully.");
            Logger.log("Executors shut down successfully.");
        } catch (InterruptedException e) {
            System.err.println("Shutdown interrupted: " + e.getMessage());
            Logger.log("Shutdown interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupt status
        } catch (Exception e) {
            System.err.println("Error while shutting down executors: " + e.getMessage());
            Logger.log("Error while shutting down executors: " + e.getMessage());
        }
    }

}