import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import './AddService.css'; // Replace with your actual CSS filename

const ServicePage = () => {
  const [services, setServices] = useState([]);
  const [editService, setEditService] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || user.role !== "Service provider") {
      navigate("/login");
    } else {
      // Fetch only services belonging to the logged-in service provider
      axios.get("http://localhost:8080/api/services", {
        params: { userEmail: user.email }
      })
      .then((response) => setServices(response.data))
      .catch((error) => console.error("Error fetching services:", error));
    }
  }, [navigate]);

  const handleDeleteService = (id) => {
    axios.delete(`http://localhost:8080/api/services/delete/${id}`)
      .then(() => setServices(services.filter((s) => s.id !== id)))
      .catch((error) => console.error("Error deleting service:", error));
  };

  const handleUpdateService = (e) => {
    e.preventDefault();
    axios.put(`http://localhost:8080/api/services/update/${editService.id}`, editService)
      .then((response) => {
        setServices(services.map((s) =>
          s.id === response.data.id ? response.data : s
        ));
        setEditService(null);
      })
      .catch((error) => console.error("Error updating service:", error));
  };

  const displayValue = (value) => (value == null || value === '' ? '-' : value);

  return (
    <div className="service-page">
      <h2>Your Services</h2>

      {editService ? (
        <div className="edit-form">
          <h3>Edit Service</h3>
          <form onSubmit={handleUpdateService}>
            <input
              type="text"
              value={editService.companyName || ''}
              onChange={(e) => setEditService({ ...editService, companyName: e.target.value })}
              placeholder="Company Name"
            />
            <input
              type="text"
              value={editService.nameOfPhotographer || ''}
              onChange={(e) => setEditService({ ...editService, nameOfPhotographer: e.target.value })}
              placeholder="Photographer's Name"
            />
            <input
              type="text"
              value={editService.hallName || ''}
              onChange={(e) => setEditService({ ...editService, hallName: e.target.value })}
              placeholder="Hall Name"
            />
            <input
              type="text"
              value={editService.hallAddress || ''}
              onChange={(e) => setEditService({ ...editService, hallAddress: e.target.value })}
              placeholder="Hall Address"
            />
            <input
              type="number"
              value={editService.hallCapacity || ''}
              onChange={(e) => setEditService({ ...editService, hallCapacity: e.target.value })}
              placeholder="Hall Capacity"
            />
            <input
              type="text"
              value={editService.description || ''}
              onChange={(e) => setEditService({ ...editService, description: e.target.value })}
              placeholder="Description"
            />
            <input
              type="number"
              value={editService.price || ''}
              onChange={(e) => setEditService({ ...editService, price: e.target.value })}
              placeholder="Price"
            />
            <input
              type="number"
              value={editService.advancePayment || ''}
              onChange={(e) => setEditService({ ...editService, advancePayment: e.target.value })}
              placeholder="Advance Payment"
            />
            <textarea
              value={editService.bookingConditions || ''}
              onChange={(e) => setEditService({ ...editService, bookingConditions: e.target.value })}
              placeholder="Booking Conditions"
            />
            <div className="edit-buttons">
              <button type="submit">Update Service</button>
              <button type="button" onClick={() => setEditService(null)}>Cancel</button>
            </div>
          </form>
        </div>
      ) : (
        <div className="service-table">
          <table>
            <thead>
              <tr>
                <th>Service Type</th>
                <th>Company Name</th>
                <th>Photographer</th>
                <th>Hall Name</th>
                <th>Hall Address</th>
                <th>Hall Capacity</th>
                <th>Description</th>
                <th>Price</th>
                <th>Advance</th>
                <th>Booking Conditions</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {services.map((service) => (
                <tr key={service.id}>
                  <td>{displayValue(service.serviceType)}</td>
                  <td>{displayValue(service.companyName)}</td>
                  <td>{displayValue(service.nameOfPhotographer)}</td>
                  <td>{displayValue(service.hallName)}</td>
                  <td>{displayValue(service.hallAddress)}</td>
                  <td>{displayValue(service.hallCapacity)}</td>
                  <td>{displayValue(service.description)}</td>
                  <td>{displayValue(service.price)}</td>
                  <td>{displayValue(service.advancePayment)}</td>
                  <td>{displayValue(service.bookingConditions)}</td>
                  <td>
                    <button onClick={() => setEditService(service)}>Edit</button>
                    <button onClick={() => handleDeleteService(service.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ServicePage;
