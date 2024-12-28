import React, { useState, useEffect } from "react";
import "./ViewConfig.css"
import axios from "axios";

const ViewConfig = () => {
    const [configurations, setConfigurations] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // Assuming you have an API endpoint to fetch configurations
    const fetchConfigurations = async () => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.get("http://localhost:8000/config/viewConfig");
            setConfigurations(response.data);
        } catch (error) {
            console.error("Error fetching configurations:", error);
            setError("Failed to load configurations. Please try again.");
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchConfigurations();
    }, []); // Run fetchConfigurations on component mount

    return (
        <div>


            {isLoading && <p>Loading configurations...</p>}

            {error && <p className="error-message">{error}</p>}

            {configurations && !isLoading && !error && (
                <div className="config-container">
                    <h2>Current Configurations</h2>
                    <ul>
                        {/* Loop through configurations object and display key-value pairs */}
                        {Object.entries(configurations).map(([key, value]) => (
                            <li key={key}>
                                <b>{key}:</b> {value}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default ViewConfig;