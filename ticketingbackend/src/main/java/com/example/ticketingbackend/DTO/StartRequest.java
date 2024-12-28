package com.example.ticketingbackend.DTO;

import com.example.ticketingbackend.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StartRequest {
    private List<Integer> vendorIds;
    private int eventId;
    private List<Customer> customers;
}
