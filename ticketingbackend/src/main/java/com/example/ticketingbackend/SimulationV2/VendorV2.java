package com.example.ticketingbackend.SimulationV2;

import com.example.ticketingbackend.Entities.Event;
import com.example.ticketingbackend.Entities.Ticket;
import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Logger.Logger;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class VendorV2 implements Runnable {
    private final Ticketpool ticketPool;
    private final DataInitializer data;
    private final int eventId;
    private int vendorId;
    private int ticketsAdded;

    public VendorV2(Ticketpool ticketPool, DataInitializer data,  int eventId, int vendorId){
        this.ticketPool=ticketPool;
        this.data=data;
        this.eventId=eventId;
        this.vendorId=vendorId;
    }
    @Override
    public void run() {
        data.InitializeEvent(eventId);
        data.IntializeConfiguration();
        int count=0;
        synchronized (ticketPool) {
            Map<Integer, Integer> VendorTickets = data.VendorsAndAssignedTickets(eventId);
            int assignedNoOfTickets = VendorTickets.getOrDefault(vendorId,0);
            Vendor vendor = data.getVendorById(vendorId);
            Event event = data.getEvent();
            for (int i = 0; i < assignedNoOfTickets; i++) {
                // generating ticket ids
                Ticket ticket = data.generateTicket(vendor,event);
                ticketPool.addTicket(ticket, eventId);
                // Couting Tickets Added
                AtomicInteger ticketCounter = new AtomicInteger(001);
                ticketsAdded = ticketCounter.incrementAndGet();

                count++;
                System.out.println("by Vendor: "+vendorId +" Executed By: "+ Thread.currentThread().getName());
                Logger.log("by Vendor: "+vendorId +"Executed By: " + Thread.currentThread().getName());
            }

        }
        try {
            Thread.sleep( data.ReleaseRate()*500L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Vendor "+vendorId+" Finished Adding Tickets.\n" +
                " Executed by: "+Thread.currentThread().getName()+" Added Count: "+count);
        Logger.log("Vendor "+vendorId+" Finished Adding Tickets.\n" +
                " Executed by: "+Thread.currentThread().getName()+" Added Count: "+count);

    }
}
