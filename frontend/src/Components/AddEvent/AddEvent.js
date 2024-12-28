import React, { useState } from "react";
import "./AddEvent.css";
import axios from 'axios';

const AddEvent = () => {
    const [formData, setFormData] = useState({
        eventName: "",
        location: "",
        eventDate: "",
        eventTime: "",
        noOfTickets: "",
        noOfVendors: "",
    });

    const [isFormVisible, setIsFormVisible] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Format date and time for backend
        const formattedData = {
            ...formData,
            eventDate: formData.eventDate, // Already in YYYY-MM-DD format
            eventTime: `${formData.eventDate}T${formData.eventTime}:00`, // Combine date and time
        };

        console.log("Formatted Data Submitted: ", formattedData);

        try {
            const response = await axios.post("http://localhost:8000/event/addEvent", formattedData);
            console.log("Event added successfully:", response.data);
            alert("Event added successfully!");
        } catch (error) {
            console.error("Error adding event:", error);
            alert("Failed to add the event. Please try again.");
        }
    };

    return (
        <div>
            <button className="centered-button" onClick={() => setIsFormVisible(true)}>
                Add Event
            </button>

            {isFormVisible && (
                <div className="form-overlay">
                    <div className="form-container">
                        <h2>Add Event</h2>
                        <form onSubmit={handleSubmit}>
                            <label>
                                Event Name:
                                <input
                                    type="text"
                                    name="eventName"
                                    value={formData.eventName}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Location:
                                <input
                                    type="text"
                                    name="location"
                                    value={formData.location}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Event Date:
                                <input
                                    type="date"
                                    name="eventDate"
                                    value={formData.eventDate}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Event Time:
                                <input
                                    type="time"
                                    name="eventTime"
                                    value={formData.eventTime}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Number of Tickets:
                                <input
                                    type="number"
                                    name="noOfTickets"
                                    value={formData.noOfTickets}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Number of Vendors:
                                <input
                                    type="number"
                                    name="noOfVendors"
                                    value={formData.noOfVendors}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <button type="submit">Add Event</button>
                        </form>
                        <button className="close-button" onClick={() => setIsFormVisible(false)}>
                            Close
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default AddEvent;
