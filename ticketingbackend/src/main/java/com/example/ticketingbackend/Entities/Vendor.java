package com.example.ticketingbackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor{
  //  vendorId	vendor_name
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "vendor_id")
    private int vendorId;

    private String vendorName;
    private String vendorEmail;


    @OneToMany(mappedBy = "vendors")
    private List<Ticket> tickets;

    // newly added code
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<VendorEvents> vendorEvents = new ArrayList<>();


}
