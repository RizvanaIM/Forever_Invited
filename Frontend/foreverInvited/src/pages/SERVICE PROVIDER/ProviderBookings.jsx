import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ProviderBookings = () => {
  const [bookings, setBookings] = useState([]);
  const user = JSON.parse(localStorage.getItem('user'));
  const email = user?.email;

  useEffect(() => {
    if (email) {
      axios.get('http://localhost:8080/api/services/provider-bookings', {
        params: { email }
      })
      .then(res => setBookings(res.data))
      .catch(err => console.error('Error fetching provider bookings:', err));
    }
  }, [email]);

  const updateStatus = (bookingId, status) => {
    axios.post('http://localhost:8080/api/services/provider-bookings/update', null, {
      params: { bookingId, status }
    })
    .then(() => {
      setBookings(prev =>
        prev.map(b =>
          b.id === bookingId ? { ...b, providerStatus: status } : b
        )
      );
    })
    .catch(err => console.error('Error updating booking status:', err));
  };

  return (
    <div>
      <h2>Bookings for Your Services</h2>
      {bookings.length === 0 ? (
        <p>No bookings found.</p>
      ) : (
        bookings.map(booking => (
          <div key={booking.id} className="booking-card">
            <h4>{booking.service.serviceType}</h4>
            <p><strong>Booked By:</strong> {booking.user.name} ({booking.user.email})</p>
            <p><strong>Booking Date:</strong> {booking.bookedDate}</p>
            <p><strong>Status:</strong> {booking.status}</p>
            <p><strong>Provider Response:</strong> {booking.providerStatus || "Pending"}</p>

            {booking.providerStatus == null && (
              <>
                <button onClick={() => updateStatus(booking.id, "Accepted")}>Accept</button>
                <button onClick={() => updateStatus(booking.id, "Rejected")}>Reject</button>
              </>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default ProviderBookings;
