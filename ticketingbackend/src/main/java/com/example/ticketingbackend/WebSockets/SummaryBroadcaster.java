package com.example.ticketingbackend.WebSockets;

import com.example.ticketingbackend.SimulationV2.CustomerV2;
import com.example.ticketingbackend.SimulationV2.DataInitializer;


import java.io.IOException;

public class SummaryBroadcaster {
    private TicketSummaryHandler summaryHandler;

    private CustomerV2 customerV2;
    private DataInitializer dataInitializer;
    public void broadcastSummary(){
        String summary = generateSummary();
        try{
            summaryHandler.broadCastSummary(summary);
        }catch (IOException e){
            System.out.println("Error Broadcasting Message "+e.getMessage());
        }
    }
    public String generateSummary(){
        int ticketsSold = customerV2.getTicketsSold();
        double revenue = customerV2.getRevenue();


        return "For "+dataInitializer.getEventName()+"\n" +
                "Tickets Sold: "+ticketsSold+"\n" +
                "Revenue Earned: "+revenue;

    }
}
