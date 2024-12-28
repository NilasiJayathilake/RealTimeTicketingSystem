package com.example.ticketingbackend.Repository;

import com.example.ticketingbackend.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketingRepository extends JpaRepository<Ticket, Integer> {
}
