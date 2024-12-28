import React, { useState } from "react";
import "./AddConfig.css";
import axios from "axios";

const AddConfiguration = () => {
    const [configData, setConfigData] = useState({
        maxTicketCapacity: 20,
        ticketRate: 1000.0,
        releaseRate: 0,
        retrievalRate: 4,
    });

    const [isFormVisible, setIsFormVisible] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setConfigData({
            ...configData,
            [name]: name === "ticketRate" || name === "releaseRate" || name === "retrievalRate"
                ? parseFloat(value)
                : parseInt(value),
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        console.log("Configuration Data Submitted:", configData);

        try {
            const response = await axios.post("http://localhost:8000/config/saveConfig", configData);
            console.log("Configuration added successfully:", response.data);

        } catch (error) {
            console.error("Error adding configuration:", error);

        }
    };

    return (
        <div>
            <button className="second-button" onClick={() => setIsFormVisible(true)}>Add Configuration</button>

            {isFormVisible && (
                <div className="form-overlay">
                    <div className="form-container">
                        <h2>Add Configuration</h2>
                        <form onSubmit={handleSubmit}>
                            <label>
                                Max Ticket Capacity:
                                <input
                                    type="number"
                                    name="maxTicketCapacity"
                                    value={configData.maxTicketCapacity}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Ticket Rate:
                                <input
                                    type="number"
                                    name="ticketRate"
                                    value={configData.ticketRate}
                                    onChange={handleChange}
                                    step="0.01"
                                    required
                                />
                            </label>

                            <label>
                                Release Rate:
                                <input
                                    type="number"
                                    name="releaseRate"
                                    value={configData.releaseRate}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <label>
                                Retrieval Rate:
                                <input
                                    type="number"
                                    name="retrievalRate"
                                    value={configData.retrievalRate}
                                    onChange={handleChange}
                                    required
                                />
                            </label>

                            <button type="submit" >Add Configuration</button>
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

export default AddConfiguration;
