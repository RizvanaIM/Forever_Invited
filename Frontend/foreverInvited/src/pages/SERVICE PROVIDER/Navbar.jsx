// /src/components/Navbar.jsx

import React from 'react';
import { Link } from 'react-router-dom';
import './navbar.css'; // Include CSS for navbar

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="nav-list">
        <li><Link to="/service-provider-dashboard" className="nav-link">Dashboard</Link></li>
        <li><Link to="/addservice" className="nav-link">Add Your Service</Link></li>
        <li><Link to="/services" className="nav-link">Services</Link></li>
        <li><Link to="/ProviderBookings" className="nav-link">Confirmed Bookings</Link></li>
        <li><Link to="/login" onClick={() => localStorage.removeItem('user')}>
              <button className="logout-button">Logout</button>
            </Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
