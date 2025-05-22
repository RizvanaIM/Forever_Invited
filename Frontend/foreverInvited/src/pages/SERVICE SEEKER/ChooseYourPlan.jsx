import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './ChooseYourPlan.css';


const ChooseYourPlan = () => {
  const [services, setServices] = useState([]);
  const [filteredServices, setFilteredServices] = useState([]);
  const [ratings, setRatings] = useState({});
  const [currentUser, setCurrentUser] = useState(null);
  const [serviceTypes, setServiceTypes] = useState([]);
  const [selectedType, setSelectedType] = useState("All");
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
        setFilteredServices(response.data);

        // Get unique service types
        const types = Array.from(new Set(response.data.map(s => s.serviceType).filter(Boolean)));
        setServiceTypes(["All", ...types]);
      })
      .catch(error => console.error('Error fetching services!', error));
  }, []);

  useEffect(() => {
    services.forEach(service => {
      axios.get(`http://localhost:8080/api/ratings/average/${service.id}`)
        .then(res => {
          setRatings(prev => ({ ...prev, [service.id]: res.data }));
        });
    });
  }, [services]);

  const handleFilterChange = (type) => {
    setSelectedType(type);
    if (type === "All") {
      setFilteredServices(services);
    } else {
      setFilteredServices(services.filter(s => s.serviceType === type));
    }
  };

  const addToCart = (serviceId) => {
    if (!currentUser?.email) {
      alert("User not found.");
      return;
    }

    axios.post(`http://localhost:8080/api/services/cart/add?email=${currentUser.email}&id=${serviceId}`)
      .then(() => alert('Your service has been added to the cart'))
      .catch(error => console.error('Error adding to cart!', error));
  };

  const submitRating = (serviceId, value) => {
    axios.post(`http://localhost:8080/api/ratings/add?serviceId=${serviceId}&userEmail=${currentUser.email}&ratingValue=${value}`)
      .then(() => {
        alert("Rating submitted!");
        setRatings(prev => ({ ...prev, [serviceId]: value }));
      })
      .catch(err => console.error("Error submitting rating", err));
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

    return Object.entries(fieldsToDisplay).map(([label, value]) =>
      value ? <p key={label}><strong>{label}:</strong> {value}</p> : null
    );
  };

  return (
    <div className="choose-your-plan">
      <h2>Choose Your Plan</h2>

      {/* Filter Dropdown */}
      <div style={{ marginBottom: '20px' }}>
        <label htmlFor="filter"><strong>Filter by Service Type:</strong> </label>
        <select
          id="filter"
          value={selectedType}
          onChange={(e) => handleFilterChange(e.target.value)}
        >
          {serviceTypes.map((type) => (
            <option key={type} value={type}>{type}</option>
          ))}
        </select>
      </div>

      <div className="service-list">
        {filteredServices.map((service) => (
          <div key={service.id} className="service-card">
            <h3>Service #{service.id}</h3>
            {renderServiceDetails(service)}

            <p><strong>Avg Rating:</strong> {ratings[service.id]?.toFixed(1) || "No ratings yet"}</p>

            <div>
              {[1, 2, 3, 4, 5].map((value) => (
                <button key={value} onClick={() => submitRating(service.id, value)}>
                  {value} ⭐
                </button>
              ))}
            </div>

            <button onClick={() => addToCart(service.id)}>Add to Cart</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ChooseYourPlan;
