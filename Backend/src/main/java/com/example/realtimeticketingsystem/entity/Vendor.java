package com.example.realtimeticketingsystem.entity;

import com.example.realtimeticketingsystem.service.ConfigurationService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Vendor implements Runnable{
    private ConfigurationService configurationService;
    @Autowired
    public Vendor(ConfigurationService configurationService){
        this.configurationService=configurationService;
    }

    // Vendor Logic is as follows:
    //          Every Vendor is assigned to a certain Event.
    //          An Event Can Have multiple Vendors who release its tickets
    //          Vendors needs to know the TicketRate, maximum Capacity and No of Tickets
    //          Event Name Or ID is also needed here so the vendors know what events they are producing tickets for


    @Override
    public void run() {
        // Retrieving the Event Data for Vendors as A List
        // Threads will go through these Lists and produce tickets to the ticket pool accordingly
        // The Maximum Capacity of an event is will be the size of the event's ticket pool
    }




}
