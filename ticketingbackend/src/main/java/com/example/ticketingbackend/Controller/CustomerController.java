package com.example.ticketingbackend.Controller;

import com.example.ticketingbackend.DTO.CustomerRequest;
import com.example.ticketingbackend.Entities.Customer;
import com.example.ticketingbackend.Configurations.ConfigurationService;
import com.example.ticketingbackend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    CustomerService customerService;
    ConfigurationService configurationService;

    @Autowired
    public CustomerController(CustomerService customerService, ConfigurationService configurationService) {
        this.customerService = customerService;
        this.configurationService = configurationService;
    }
    @PostMapping("/register")
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    // customer buying a ticket
//    @MessageMapping("/buy-ticket") // Client sends messages to /app/buy-ticket
//    public void buyTicket(@Payload Customer customer) {
//        customerService.buyTicket(customer);
//    }
@PostMapping("/buy/{eventId}")
public ResponseEntity<Customer> purchaseTicket(@RequestBody CustomerRequest customerRequest, @PathVariable int eventId) {
    try {
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setCustomerEmail(customerRequest.getCustomerEmail());

        // Call the service -  Passing the customer details + eventId
        Customer purchasedCustomer = customerService.buyTicket(customer,eventId);

        // Return the customer with the purchased ticket
        return ResponseEntity.ok(purchasedCustomer);

    } catch (RuntimeException e) {
        // Handle any runtime exceptions and return a bad request response
        return ResponseEntity.badRequest().body(null);
    }
}
}

