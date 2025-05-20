import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import Navbar from './Navbar';
import './serviceProviderDashboard.css';

const ServiceProDashboard = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user || user.role !== "Service provider") {
      navigate("/login");
    } else {
      setUsername(user.name);
    }
  }, [navigate]);

  return (
    <div className="dashboard-page">
      <Navbar />
      <div className="dashboard-container">
        <h1 className="dashboard-heading">
          Hi {username}!! Provide your Services Via Our FOREVER INVITED
        </h1>
        <div className="card-container">
          <Link to="/addservice">
            <button className="add-service-button">Add Your Service</button>
          </Link>
          <div className="service-info">
            <p>Manage your services easily and get booked by customers!</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ServiceProDashboard;
