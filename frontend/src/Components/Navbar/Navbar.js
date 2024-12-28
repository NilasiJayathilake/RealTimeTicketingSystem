import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";
import logo from './ghost.png'


const Navbar = () => {
    const [menu, setMenu] = useState("navbar-home");

    return (
        <div className="navbar">
            <img src={logo} alt="Logo" />
            <ul className="navbar-menu">
                <li className={menu === "navbar-home" ? "active" : ""}>
                    <Link to="/" onClick={() => setMenu("navbar-home")}>Home</Link>
                </li>
                <li className={menu==="navbar-analytics"?"active":""}>
                    <Link to="/analytics/1" onClick={() => setMenu("navbar-analytics")}>Analytics</Link>
                </li>

                <li className={menu === "navbar-simulation" ? "active" : ""}>
                    <Link to="/simulation/1" onClick={() => setMenu("navbar-simulation")}>Simulation</Link>
                </li>

                <li className={menu === "navbar-notification" ? "active" : ""}>
                    <Link to="/notification" onClick={() => setMenu("navbar-notification")}>Notification</Link>
                </li>
            </ul>

            <button className="sign-in-button">Sign Out</button>
        </div>
    );
};



export default Navbar;