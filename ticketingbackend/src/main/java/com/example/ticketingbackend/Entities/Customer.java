package com.example.ticketingbackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    // 	customer_id	customer_name	ticket_id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int customerId;

    private String customerName;
    private String customerEmail;

    @ManyToOne // A customer can buy one or many tickets
    @JoinColumn(name = "ticketId")
    private Ticket ticket;
}
