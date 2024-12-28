package com.example.ticketingbackend.Repository;


import com.example.ticketingbackend.Entities.Event;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository <Event, Integer> {

        @Query("select e from Event e where e.eventId = :event_id")
        Event getEvent(int event_id);

        @Query("SELECT e FROM Event e ORDER BY e.eventId DESC")
        List<Event> findLatestEvents(Pageable pageable);
}
