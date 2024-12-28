package com.example.ticketingbackend.Repository;

import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Entities.VendorEvents;
import com.example.ticketingbackend.Entities.VendorEventsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorEventsRepository extends JpaRepository<VendorEvents, VendorEventsPK> {
    Optional<VendorEvents> findByVendorVendorIdAndEventEventId(int vendorId, int eventId);

    @Query("SELECT ve.vendor FROM VendorEvents ve WHERE ve.event.eventId = :eventId")
    List<Vendor> findVendorsByEventId(@Param("eventId") int eventId);

    @Query("SELECT ve.vendor.vendorId FROM VendorEvents ve WHERE ve.event.eventId = :eventId")
    List<Integer> findVendorIdsByEventId(@Param("eventId") int eventId);

}
