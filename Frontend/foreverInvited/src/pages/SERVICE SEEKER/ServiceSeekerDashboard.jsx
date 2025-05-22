import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './ServiceSeekerDashboard.css';

const ServiceSeekerDashboard = () => {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [username, setUsername] = useState('');

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || user.role !== 'Service seeker') {
      navigate('/login');
    } else {
      setUsername(user.name);
      fetchBookings(user.email);
    }
  }, [navigate]);

  const fetchBookings = async (email) => {
    try {
      const response = await fetch(`http://localhost:8080/api/services/bookedServices?email=${email}`);
      const data = await response.json();
      setBookings(data);
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  };

  const handleChoosePlan = () => {
    navigate('/chooseplan');
  };

  return (
    <div className="dashboard-container">
      <nav className="dashboard-nav">
        <div className="nav-left">
          <h2>Forever Invited</h2>
        </div>
        <div className="nav-right">
          <button onClick={() => navigate('/chooseplan')}>Choose Plan</button>
          <button onClick={() => navigate('/bookings')}>Your Bookings</button>
          <button onClick={() => navigate('/service-seeker-dashboard/view-cart')}>Cart</button>
          <button onClick={() => navigate('/generate-invitation')}>Generate Invitation</button>
          <button onClick={() => navigate('/notifications')}>Notifications</button>
          <div className="user-menu">
            <span>{username}</span>
            <button onClick={() => navigate('/profile')}>Profile Settings</button>
            <button onClick={() => navigate('/logout')}>Logout</button>
          </div>
        </div>
      </nav>

      <main className="dashboard-main">
        <div className="welcome-section">
          <h1>Hi {username}, Welcome to FOREVER INVITED</h1>
          <p>Plan your wedding with us</p>
          <button className="choose-plan-btn" onClick={handleChoosePlan}>
            Choose Your Plan
          </button>
        </div>

        <div className="bookings-section">
          <h2>Your Bookings</h2>
          <table className="bookings-table">
            <thead>
              <tr>
                <th>Service ID</th>
                <th>Service</th>
                <th>Company / Hall Name</th>
                <th>Booked Date</th>
                <th>Status</th>
                <th>Provider Response</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((booking) => (
                <tr key={booking.id}>
                  <td>{booking.service?.id || '-'}</td>
                  <td>{booking.service?.serviceType || '-'}</td>
                  <td>{booking.service?.companyName || booking.service?.hallName || '-'}</td>
                  <td>{booking.bookedDate ? new Date(booking.bookedDate).toLocaleDateString() : '-'}</td>
                  <td className={`status-${(booking.status || 'unknown').toLowerCase()}`}>
                    {booking.status || '-'}
                  </td>
                  <td className={`status-${(booking.providerStatus || 'pending').toLowerCase()}`}>
                    {booking.providerStatus || 'Pending'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
};

export default ServiceSeekerDashboard;
