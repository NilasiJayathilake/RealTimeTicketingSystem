import {useEffect, useState} from "react";
function EventSummaryUpdate(){
    const[update, setUpdate] = useState({ticketsAdded: 0, ticketsPurchased: 0, revenue:0});
    useEffect(() => {
        const socket = new WebSocket("ws://localhost:8000/summary");

        socket.onopen = () => {
            console.log("WebSocket connection established.");
        };

        socket.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                console.log("Message received:", data);
                setUpdate(data);
            } catch (error) {
                console.error("Error parsing message:", error);
            }
        };

        socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        socket.onclose = (event) => {
            console.warn("WebSocket closed:", event);
        };

        return () => {
            console.log("Closing WebSocket connection.");
            socket.close();
        };
    }, []);

    return(
        <div>
            <div>
                <h1>Event Updates</h1>
                <p>Tickets Added: {update.ticketsAdded}</p>
                <p>Tickets Purchased: {update.ticketsPurchased}</p>
                <p>Revenue Earned: ${update.revenue.toFixed(2)}</p>
            </div>
        </div>
    );

}
export default EventSummaryUpdate;
