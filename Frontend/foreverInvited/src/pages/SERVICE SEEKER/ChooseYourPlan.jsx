import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ChooseYourPlan = () => {
  const [services, setServices] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || user.role !== "Service seeker") {
      navigate("/login");
    } else {
      setCurrentUser(user);
    }
  }, [navigate]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/services')
      .then(response => {
        setServices(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching services!', error);
      });
  }, []);

  const addToCart = (serviceId) => {
    if (!currentUser || !currentUser.email) {
      alert("User not found.");
      return;
    }

    axios.post(`http://localhost:8080/api/services/cart/add?email=${currentUser.email}&id=${serviceId}`)
      .then(() => {
        alert('Your service has been added to the cart');
      })
      .catch(error => {
        console.error('There was an error adding to the cart!', error);
      });
  };

  const renderServiceDetails = (service) => {
    const fieldsToDisplay = {
      'Service Type': service.serviceType,
      'Company Name': service.companyName,
      'Photographer': service.nameOfPhotographer,
      'Hall Name': service.hallName,
      'Hall Address': service.hallAddress,
      'Hall Capacity': service.hallCapacity,
      'Price': service.price,
      'Advance Payment': service.advancePayment,
      'Description': service.description,
      'Booking Conditions': service.bookingConditions,
      'Provided By': service.user?.email
    };

    return Object.entries(fieldsToDisplay).map(([label, value]) => {
      if (value !== null && value !== undefined && value !== '') {
        return (
          <p key={label}><strong>{label}:</strong> {value}</p>
        );
      }
      return null;
    });
  };

  return (
    <div className="choose-your-plan">
      <h2>Choose Your Plan</h2>
      <div className="service-list">
        {services.map((service) => (
          <div key={service.id} className="service-card">
            <h3>Service #{service.id}</h3>
            {renderServiceDetails(service)}
            <button onClick={() => addToCart(service.id)}>Add to Cart</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ChooseYourPlan;
