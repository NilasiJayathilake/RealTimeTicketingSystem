package com.example.realtimeticketingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ticket_Configuration_Table")
@Data
public class ConfigurationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String eventName;
    private Date eventDate;
    private int noOfTickets;
    private double ticketRate;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int retrievalRate;
}
