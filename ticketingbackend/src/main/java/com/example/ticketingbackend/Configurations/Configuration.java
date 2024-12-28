package com.example.ticketingbackend.Configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


/* Not annotating as an Entity cause,
        - Configuration will be saved in a json and used to load easily.
        - No point in saving it in sql as it can change according to the simulation
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Configuration {
    private int maxTicketCapacity;
    private double ticketRate;
    private int releaseRate; // between 1 and 10
    private int retrievalRate; // between 1 and 10
}
