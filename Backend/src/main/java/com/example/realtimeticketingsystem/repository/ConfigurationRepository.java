package com.example.realtimeticketingsystem.repository;

import com.example.realtimeticketingsystem.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConfigurationRepository extends JpaRepository <ConfigurationEntity, Integer>{
        @Query("SELECT c.ConfigurationEntity FROM ConfigurationEntity  c ORDER BY c.id ASC")
        ConfigurationEntity getConfigurationEntitiesByIdOrderById();

        // creating custom queries cause these methods are not in JPARepository
        // Accessing all data in order to stimulate multiple vendors and multiple customers accessing multiple events
        // To get all Event Names
        @Query("SELECT c.eventName FROM ConfigurationEntity c ORDER BY c.id ASC")
        List<String> getAllEventNamesOrderByIdAsc();

        // To get all no of Tickets
        @Query("SELECT c.noOfTickets FROM ConfigurationEntity c ORDER BY c.id ASC")
        List<Integer> getAllNoOfTicketsOrderByIdAsc();

        // To get all Ticket Rates
        @Query("SELECT c.ticketRate FROM ConfigurationEntity c ORDER BY c.id ASC ")
        List<Double> getAllTicketRateOrderByIdAsc();


}
