// import { useState, useEffect } from 'react';
// import axios from "axios";
//
// const SoldTickets = () => {
//     const [soldTickets, setSoldTickets] = useState(0);
//
//     useEffect(() => {
//         const fetchSoldTickets = async () => {
//             try {
//                 const response = await fetch("http://localhost:8080/simulation/soldtickets");
//                 const data = await response.json();
//                 setSoldTickets(data);
//             } catch (error) {
//                 console.error('Error fetching sold tickets:', error);
//             }
//         };
//
//
//         fetchSoldTickets();
//         const interval = setInterval(fetchSoldTickets, 5000); // Poll every 5 seconds
//
//         return () => clearInterval(interval); // Cleanup on unmount
//     }, []);
//
//     return (
//         <div>
//             <h1>Sold Tickets: {soldTickets}</h1>
//         </div>
//     );
// };
//
// export default SoldTickets;
