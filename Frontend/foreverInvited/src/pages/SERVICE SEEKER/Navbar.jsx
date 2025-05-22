// src/Pages/SERVICE_SEEKER_DASHBOARD/Navbar.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul>
        <li><Link to="/service-seeker-dashboard">Choose Your Plan</Link></li>
        <li><Link to="/service-seeker-dashboard/view-cart">View Cart</Link></li>
        <li><Link to="/service-seeker-dashboard/invitation-sharing">Invitation Sharing</Link></li>
        <li><Link to="/service-seeker-dashboard/confirmed-booking">Confirmed Booking</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;
