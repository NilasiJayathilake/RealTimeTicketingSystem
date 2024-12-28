import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const EventAnalytics = () => {
    const { eventId } = useParams();
    const [eventData, setEventData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchEvent = async () => {
            try {
                const response = await fetch(`http://localhost:8000/event/loadEvent/${eventId}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setEventData(data);
                setLoading(false);
            } catch (err) {
                console.error("Failed to load event data:", err);
                setError("Failed to load event data. Please try again later.");
                setLoading(false);
            }
        };

        fetchEvent();
    }, [eventId]);

    if (loading) return <div>Loading event data...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <h2>{eventData.eventName}</h2>
            <p><strong>Location:</strong> {eventData.location}</p>
            <p><strong>Date:</strong> {new Date(eventData.eventDate).toLocaleDateString()}</p>
            <p><strong>Time:</strong> {new Date(eventData.eventTime).toLocaleTimeString([], {
                hour: '2-digit',
                minute: '2-digit'
            })}</p>
            <p><strong>No Of Tickets:</strong> {eventData.noOfTickets}</p>
            <p><strong>No Of Vendors:</strong> {eventData.noOfVendors}</p>


        </div>
    );
};

export default EventAnalytics;