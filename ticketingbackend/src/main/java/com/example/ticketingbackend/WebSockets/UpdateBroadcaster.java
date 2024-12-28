package com.example.ticketingbackend.WebSockets;

import com.example.ticketingbackend.SimulationV2.CustomerV2;
import com.example.ticketingbackend.SimulationV2.Ticketpool;
import com.example.ticketingbackend.SimulationV2.VendorV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateBroadcaster {
    @Autowired
    private TicketSummaryHandler ticketSummaryHandler;
    CustomerV2 customerV2;
    VendorV2 vendorV2;
    @Scheduled(fixedRate = 2000)
    public void broadcastSummaryUpdate() throws IOException {
        String summary = generateUpdate();
        ticketSummaryHandler.broadCastSummary(summary);
    }

    private String generateUpdate() {
        // Replace with your logic to fetch real-time data
        int ticketsAdded = vendorV2.getTicketsAdded();
        int ticketsPurchased = customerV2.getTicketsSold();
        double revenue = customerV2.getRevenue();

        return String.format("{\"ticketsAdded\": %d, \"ticketsPurchased\": %d, \"revenue\": %.2f}",
                ticketsAdded, ticketsPurchased, revenue);
    }

}
