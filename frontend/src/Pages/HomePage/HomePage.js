import React from "react";
import AddEvent from "../../Components/AddEvent/AddEvent";
import ViewEvents from "../../Components/ViewEvents/ViewEvents";

import "./HomePage.css";
import AddConfig from "../../Components/AddConfiguration/AddConfig";
import ViewConfig from "../../Components/ViewConfig/ViewConfig";

// Can View Past Events
// Can view Ongoing Events

const HomePage = () => {
    return (
        <div className="homepage-container">
            <div className="welcome">
                <h1>Ticket Ghost Welcomes You</h1>
                <h3>Admin Dashboard</h3>
            </div>
            <div className="homepage-content">
                <div className="add-event-section">
                    <AddEvent/>
                    <AddConfig/>
                    <ViewConfig/>
                </div>
                <div className="view-events-section">

                    <ViewEvents/>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
