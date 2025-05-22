import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AddService.css';

const AddService = () => {
  const navigate = useNavigate();
  const [selectedService, setSelectedService] = useState('Wedding Photography');
  const [formData, setFormData] = useState({
    serviceType: 'Wedding Photography',
    companyName: '',
    price: '',
    advancePayment: '',
    description: '',
    bookingConditions: '',
    nameOfPhotographer: '',
    hallName: '',
    hallAddress: '',
    hallCapacity: '',
  });

  // Redirect if not a logged-in service provider
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || user.role !== 'Service provider') {
      navigate('/login');
    }
  }, [navigate]);

  const handleServiceChange = (e) => {
    const selected = e.target.value;
    setSelectedService(selected);
    setFormData({
      serviceType: selected,
      companyName: '',
      price: '',
      advancePayment: '',
      description: '',
      bookingConditions: '',
      nameOfPhotographer: '',
      hallName: '',
      hallAddress: '',
      hallCapacity: '',
    });
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
      alert('You must be logged in.');
      navigate('/login');
      return;
    }

    const processedData = Object.fromEntries(
      Object.entries(formData).map(([key, value]) => [key, value === '' ? null : value])
    );

    axios
      .post(`http://localhost:8080/api/provider/add-service?userEmail=${user.email}`, processedData)
      .then(() => alert('Service added successfully'))
      .catch((error) => {
        console.error('Error adding service:', error);
        alert('Failed to add service. Please try again.');
      });
  };

  const renderFormFields = () => {
    return (
      <>
        {selectedService !== 'Wedding Hall' && (
          <>
            <label>Company Name</label>
            <input
              type="text"
              name="companyName"
              value={formData.companyName}
              onChange={handleChange}
              placeholder="Company Name"
              required
            />
          </>
        )}

        {selectedService === 'Wedding Photography' && (
          <>
            <label>Name of Photographer</label>
            <input
              type="text"
              name="nameOfPhotographer"
              value={formData.nameOfPhotographer}
              onChange={handleChange}
              placeholder="Photographer's Name"
              required
            />
          </>
        )}

        {selectedService === 'Wedding Hall' && (
          <>
            <label>Hall Name</label>
            <input
              type="text"
              name="hallName"
              value={formData.hallName}
              onChange={handleChange}
              placeholder="Hall Name"
              required
            />
            <label>Hall Address</label>
            <input
              type="text"
              name="hallAddress"
              value={formData.hallAddress}
              onChange={handleChange}
              placeholder="Hall Address"
              required
            />
            <label>Hall Capacity</label>
            <input
              type="number"
              name="hallCapacity"
              value={formData.hallCapacity}
              onChange={handleChange}
              placeholder="Hall Capacity"
              required
            />
          </>
        )}

        <label>Description</label>
        <textarea
          name="description"
          value={formData.description}
          onChange={handleChange}
          placeholder="Description"
          required
        />

        <label>Price</label>
        <input
          type="number"
          name="price"
          value={formData.price}
          onChange={handleChange}
          placeholder="Price"
          required
        />

        <label>Advance Payment</label>
        <input
          type="number"
          name="advancePayment"
          value={formData.advancePayment}
          onChange={handleChange}
          placeholder="Advance Payment"
          required
        />

        <label>Booking Conditions</label>
        <textarea
          name="bookingConditions"
          value={formData.bookingConditions}
          onChange={handleChange}
          placeholder="Booking Conditions"
          required
        />
      </>
    );
  };

  return (
    <div className="add-service-container">
      <h2>Add Your Service</h2>
      <form onSubmit={handleSubmit}>
        <label>Select Service</label>
        <select onChange={handleServiceChange} value={selectedService} className="dropdown">
          <option value="Wedding Photography">Wedding Photography</option>
          <option value="Event Planner Package">Event Planner Package</option>
          <option value="Catering">Catering</option>
          <option value="Sound">Sound</option>
          <option value="Wedding Hall">Wedding Hall</option>
          <option value="Decoration">Decoration</option>
        </select>

        {renderFormFields()}

        <button type="submit">Add Service</button>
      </form>
    </div>
  );
};

export default AddService;
