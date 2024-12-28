import React, { useState, useEffect } from 'react';
import '../AddEvent/AddEvent.css';
import './AssignVendors.css'

const AssignVendors = ({ onClose }) => {
    const [vendorId, setVendorId] = useState('');
    const [numTickets, setNumTickets] = useState(1);
    const [vendorExists, setVendorExists] = useState(false);
    const [validationError, setValidationError] = useState('');

    useEffect(() => {
        // Implement backend validation logic here
        const validateVendor = async () => {
            try {
                const response = await fetch(`/api/vendors/${vendorId}`); // Replace with your backend endpoint
                const data = await response.json();
                setVendorExists(true);
                setValidationError('');
            } catch (error) {
                setVendorExists(false);
                setValidationError('Vendor ID not found.');
            }
        };

        if (vendorId) {
            validateVendor();
        }
    }, [vendorId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!vendorExists) {
            return; // Prevent submission if vendor doesn't exist
        }

        // Handle form submission logic here, e.g., send data to server
        console.log('Vendor assigned:', {
            id: vendorId,
            numTickets,
        });
        onClose(); // Close the modal after submission
    };

    return (
        <div className="form-overlay">
            <div className="form-container">
                <h2>Assign Vendor</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="vendorId">Vendor ID:</label>
                        <input
                            type="text"
                            id="vendorId"
                            value={vendorId}
                            onChange={(e) => setVendorId(e.target.value)}
                            required
                        />
                        {validationError && <p className="error">{validationError}</p>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="numTickets">Number of Tickets:</label>
                        <input
                            type="number"
                            id="numTickets"
                            value={numTickets}
                            min="1"
                            onChange={(e) => setNumTickets(parseInt(e.target.value))}
                            required
                        />
                    </div>
                </form>
                <button className="submit-button" type="submit" disabled={!vendorExists}>
                    Assign Vendor
                </button>
                <button className="close-button" onClick={onClose}>
                    Close
                </button>
            </div>
        </div>
    );
};

export default AssignVendors;