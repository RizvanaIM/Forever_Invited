import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './ServiceSeekerDashboard.css';

const ServiceSeekerDashboard = () => {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [username, setUsername] = useState('');
  const [activeTab, setActiveTab] = useState('dashboard');

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
      {/* Header with Glass Effect */}
      <header className="dashboard-header">
        <div className="logo">
          <h2>Forever Invited</h2>
        </div>
        <div className="user-profile">
          <div className="user-info">
            <span className="user-name">{username}</span>
            <span className="user-role">Service Seeker</span>
          </div>
          <div className="profile-actions">
            <button className="profile-btn" onClick={() => navigate('/profile')}>
              <i className="fas fa-user"></i>
            </button>
            <button className="logout-btn" onClick={() => navigate('/logout')}>
              <i className="fas fa-sign-out-alt"></i>
            </button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="dashboard-content">
        {/* Quick Actions */}
        <section className="quick-actions">
          <div className="action-card" onClick={() => navigate('/chooseplan')}>
            <i className="fas fa-list"></i>
            <span>Choose Plan</span>
          </div>
          <div className="action-card" onClick={() => navigate('/bookings')}>
            <i className="fas fa-calendar-check"></i>
            <span>Bookings</span>
          </div>
          <div className="action-card" onClick={() => navigate('/service-seeker-dashboard/view-cart')}>
            <i className="fas fa-shopping-cart"></i>
            <span>Cart</span>
          </div>
          <div className="action-card" onClick={() => navigate('/generate-invitation')}>
            <i className="fas fa-envelope"></i>
            <span>Generate Invitation</span>
          </div>
          <div className="action-card" onClick={() => navigate('/notifications')}>
            <i className="fas fa-bell"></i>
            <span>Notifications</span>
          </div>
        </section>

        {/* Welcome Banner */}
        <section className="welcome-banner">
          <div className="welcome-content">
            <h1>Welcome back, {username}! 🎉</h1>
            <p>Ready to make your special day perfect?</p>
            <button className="cta-button" onClick={handleChoosePlan}>
              Start Planning Now
            </button>
          </div>
        </section>

        {/* Recent Bookings */}
        <section className="bookings-overview">
          <h2>Recent Bookings</h2>
          <div className="bookings-grid">
            {bookings.slice(0, 4).map((booking) => (
              <div key={booking.id} className="booking-card">
                <div className="booking-header">
                  <span className="service-type">{booking.service?.serviceType || '-'}</span>
                  <span className={`status-badge status-${(booking.status || 'unknown').toLowerCase()}`}>
                    {booking.status || '-'}
                  </span>
                </div>
                <div className="booking-body">
                  <p className="company-name">{booking.service?.companyName || booking.service?.hallName || '-'}</p>
                  <p className="booking-date">
                    <i className="far fa-calendar-alt"></i>
                    {booking.bookedDate ? new Date(booking.bookedDate).toLocaleDateString() : '-'}
                  </p>
                </div>
                <div className="booking-footer">
                  <span className={`provider-status status-${(booking.providerStatus || 'pending').toLowerCase()}`}>
                    {booking.providerStatus || 'Pending'}
                  </span>
                </div>
              </div>
            ))}
          </div>
          {bookings.length > 4 && (
            <button className="view-all-btn" onClick={() => navigate('/bookings')}>
              View All Bookings
            </button>
          )}
        </section>

        {/* Quick Stats */}
        <section className="stats-section">
          <div className="stat-card">
            <i className="fas fa-calendar-check"></i>
            <div className="stat-info">
              <h3>Total Bookings</h3>
              <span className="stat-number">{bookings.length}</span>
            </div>
          </div>
          <div className="stat-card">
            <i className="fas fa-clock"></i>
            <div className="stat-info">
              <h3>Pending</h3>
              <span className="stat-number">
                {bookings.filter(b => b.status === 'Pending').length}
              </span>
            </div>
          </div>
          <div className="stat-card">
            <i className="fas fa-check-circle"></i>
            <div className="stat-info">
              <h3>Confirmed</h3>
              <span className="stat-number">
                {bookings.filter(b => b.status === 'Confirmed').length}
              </span>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default ServiceSeekerDashboard;
