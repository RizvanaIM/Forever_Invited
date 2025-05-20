import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ViewCart = () => {
  const [cart, setCart] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));

    if (!user || user.role !== 'Service seeker') {
      navigate('/login');
    } else {
      const userEmail = user.email;

      axios.get(`http://localhost:8080/api/services/cart`, {
        params: { email: userEmail }
      })
      .then(response => setCart(response.data))
      .catch(error => console.error('Error fetching cart:', error));
    }
  }, [navigate]);

  const confirmBooking = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    const userEmail = user?.email;

    axios.post(`http://localhost:8080/api/services/confirm-booking`, null, {
      params: { email: userEmail }
    })
    .then(() => {
      alert('Booking confirmed!');
      setCart([]);
    })
    .catch(error => console.error('Error confirming booking:', error));
  };

  // Calculate totals
  const totalPrice = cart.reduce((sum, item) => sum + (item.price || 0), 0);
  const totalAdvance = cart.reduce((sum, item) => sum + (item.advancePayment || 0), 0);

  return (
    <div className="view-cart">
      <h2>Your Cart</h2>
      {cart.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="cart-list">
          {cart.map(service => (
            <div key={service.id} className="cart-item">
              <h3>{service.serviceType}</h3>
              {service.serviceType === "Wedding Hall" ? (
                <p><strong>Hall Name:</strong> {service.hallName}</p>
              ) : (
                <p><strong>Company:</strong> {service.companyName}</p>
              )}
              <p><strong>Price:</strong> ${service.price}</p>
              <p><strong>Advance Payment:</strong> ${service.advancePayment}</p>
            </div>
          ))}

          {/* Totals Section */}
          <div className="cart-totals">
            <hr />
            <p><strong>Total Price:</strong> ${totalPrice}</p>
            <p><strong>Total Advance Payment:</strong> ${totalAdvance}</p>
            <button onClick={confirmBooking}>Confirm Booking</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewCart;
