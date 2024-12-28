package com.example.ticketingbackend.Service;

import com.example.ticketingbackend.Entities.Vendor;
import com.example.ticketingbackend.Repository.VendorEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ticketingbackend.Entities.VendorEvents;

import java.util.List;

@Service
public class VendorEventsService {
    VendorEventsRepository vendorEventsRepository;

    @Autowired
    public VendorEventsService(VendorEventsRepository vendorEventsRepository) {
        this.vendorEventsRepository = vendorEventsRepository;
    }

    public int getAssignedNoOfTickets(int vendorId, int eventId) {
        return vendorEventsRepository.findByVendorVendorIdAndEventEventId(vendorId, eventId)
                .map(VendorEvents::getAssignedNoOfTickets)
                .orElseThrow(() -> new RuntimeException("VendorEvents not found for Vendor ID: "
                        + vendorId + " and Event ID: " + eventId));
    }

    public List<Vendor> getVendorsForEvent(int eventId) {
        return vendorEventsRepository.findVendorsByEventId(eventId);
    }

    public List<Integer> getVendorIdsForEvent(int eventId) {
        return vendorEventsRepository.findVendorIdsByEventId(eventId);
    }

}
