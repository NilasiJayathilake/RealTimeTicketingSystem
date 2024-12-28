
import "./Log.css"

import EventSummaryUpdate from "../WebSockets/EventSummaryUpdate";
const LogsSection = () => {
    // Function to fetch logs from the backend

    return (
        <section className="logs-section">
            <h2>Backend Logs</h2>
            <EventSummaryUpdate/>
            <div className="logs-container">

            </div>
        </section>
    );
};

export default LogsSection;
