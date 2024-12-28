package com.example.ticketingbackend.Repository;

import com.example.ticketingbackend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
