import React, { useState, useEffect } from 'react';
import './ViewAnalyticsPage.css';
import EventAnalytics from '../../Components/EventAnalytics/EventAnalytics';
import { useParams, useNavigate } from 'react-router-dom';
import AssignVendors from '../../Components/AssignVendors/AssignVendors';

const ViewAnalyticsPage = () => {
    const { eventId } = useParams();
    const navigate = useNavigate(); // Hook for navigation
    const [eventData, setEventData] = useState(null);
    const [showAssignVendorModal, setShowAssignVendorModal] = useState(false);
    const [showVendorForm, setShowVendorForm] = useState(false);

    useEffect(() => {
        // Fetch event data
        const fetchEventData = async () => {
            try {
                const response = await fetch(`http://localhost:8000/event/loadEvent/${eventId}`);
                const data = await response.json();
                setEventData(data);
            } catch (error) {
                console.error('Error fetching event data:', error);
            }
        };

        fetchEventData();
    }, [eventId]);

    const handleAddVendorClick = () => {
        setShowVendorForm(true);
    };

    const handleCloseForm = () => {
        setShowVendorForm(false);
    };

    const handleStartSimulationClick = () => {
        navigate(`/simulation/${eventId}`); // Navigate to the Start Simulation page
    };

    return (
        <div className="view-analytics-container">
            <div className="view-analytics-header">
                <h1 className="view-analytics-title">Event Analytics</h1>
            </div>
            <div className="analytics-content">
                {/* Event Analytics Component */}
                <div className="analytics-section">
                    <EventAnalytics eventId={eventId} />
                </div>

                <button className="assign-vendors-button" onClick={handleAddVendorClick}>
                    Assign Tickets to Vendors
                </button>
                <button
                    className="start-simulation-button"
                    onClick={handleStartSimulationClick}
                >
                    Start Simulation
                </button>

                {showVendorForm && (
                    <AssignVendors onClose={handleCloseForm} />
                )}
            </div>
        </div>
    );
};

export default ViewAnalyticsPage;
