import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ViewEvents.css";

const ViewEvents = () => {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const fetchLimit = 6;

    const navigate = useNavigate();

    const fetchEvents = async () => {
        try {
            const response = await fetch(`http://localhost:8000/event/latestEvents?limit=${fetchLimit}`);
            const results = await response.json();
            setEvents(results);
            setLoading(false);
        } catch (err) {
            setError("Failed to load events");
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEvents();

        // Poll every 10 seconds to fetch new events
        const intervalId = setInterval(fetchEvents, 10000);

        return () => clearInterval(intervalId); // Cleanup interval on component unmount
    }, []);

    if (loading) return <div className="loading">Loading events...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="explore-events-container">
            <h2>Explore Events</h2>
            <div className="event-cards">
                {events.map((event) => (
                    <div className="event-card" key={event.eventId}
                         onClick={() => navigate(`/analytics/${event.eventId}`)}>
                        <h3>{event.eventName}</h3>
                        <p><strong>Location:</strong> {event.location}</p>
                        <p><strong>Date:</strong> {new Date(event.eventDate).toLocaleDateString()}</p>
                        <p><strong>Time:</strong> {new Date(event.eventTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ViewEvents;
