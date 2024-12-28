package com.example.ticketingbackend.Repository;

import com.example.ticketingbackend.Entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository <Vendor, Integer> {
    List<Vendor> findAllByVendorIdIn(List<Integer> vendorIds);

    List<Vendor> findAll();


}
