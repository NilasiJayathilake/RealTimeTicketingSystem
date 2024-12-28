package com.example.ticketingbackend.SimulationV2;

import com.example.ticketingbackend.Entities.Customer;
import com.example.ticketingbackend.Entities.Ticket;
import com.example.ticketingbackend.Logger.Logger;
import lombok.Getter;

@Getter
public class CustomerV2 implements Runnable{
    private final Ticketpool ticketPool;
    private final DataInitializer data;
    private final int eventId;
    private final int customerId;
    int ticketsSold;
    double revenue;



    public CustomerV2(Ticketpool ticketPool, DataInitializer data, int eventId, int customerId){
        this.ticketPool=ticketPool;
        this.data=data;
        this.eventId=eventId;
        this.customerId=customerId;
    }

    public void run() {
        try {
            data.InitializeEvent(eventId);
            data.IntializeConfiguration();

            while (true) {
                synchronized (ticketPool.getTickets()) {
                    Ticket ticket = ticketPool.removeTicket(eventId);
                    revenue = data.TicketRate()*ticket.getTicketId();
                    ticketsSold = ticket.getTicketId();
                    System.out.println("Executed By: " + Thread.currentThread().getName() + "\n Current Pool Size: " + ticketPool.PoolSize());
                    Logger.log("Purchased By Customer"+customerId);
                    System.out.println("Revenue Earned: "+revenue);
                    Logger.log("Revenue Earned: "+revenue);
                    //simulating delay
                    Thread.sleep( data.RetrievalRate()* 500L);
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer thread interrupted: " + Thread.currentThread().getName());
            Logger.faultLog(Thread.currentThread().getName(), "Customer thread interrupted: ");
        } catch (RuntimeException e) {
            System.err.println("Error in CustomerV2: " + e.getMessage());
            Logger.faultLog(Thread.currentThread().getName(), "Error in CustomerV2: "+ e.getMessage());
        }

    }

}
