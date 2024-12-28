package com.example.ticketingbackend.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private int ticketId;
    private String ticketName;
    private double ticketRate;
    private String availability; // whether it is available to buy or sell.

    // 	ticket_id	ticket_name	ticket_rate	status	vendor_id	event_id customer_id

    @ManyToOne
    private Vendor vendors;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Customer customer;

    public Ticket(int ticketId, double v, Event event) {
    }
}