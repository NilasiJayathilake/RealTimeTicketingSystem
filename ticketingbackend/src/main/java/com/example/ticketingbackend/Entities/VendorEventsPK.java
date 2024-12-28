package com.example.ticketingbackend.Entities;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VendorEventsPK implements Serializable {
    private Vendor vendor;
    private Event event;
}
