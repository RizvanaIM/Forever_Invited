import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <div className="homepage">
      {/* Main Grid Layout */}
      <div className="grid-container">
        {/* Welcome Card */}
        <div className="card welcome-card">
          <div className="welcome-auth-buttons">
            <button className="login-btn" onClick={() => navigate('/login')}>Login</button>
            <button className="register-btn" onClick={() => navigate('/register')}>Register</button>
          </div>
          <h1>Welcome to <span className="highlight">Forever Invited</span></h1>
          <p>Plan your perfect celebration with us</p>
          <button className="cta-button" onClick={() => navigate('/chooseplan')}>
            Choose Your Plan
          </button>
        </div>

        {/* Services Card */}
        <div className="card services-card">
          <h2>Our Services</h2>
          <div className="services-grid">
            <div className="service-item">
              <i className="fas fa-calendar-alt"></i>
              <span>Event Planning</span>
            </div>
            <div className="service-item">
              <i className="fas fa-camera"></i>
              <span>Photography</span>
            </div>
            <div className="service-item">
              <i className="fas fa-utensils"></i>
              <span>Catering</span>
            </div>
            <div className="service-item">
              <i className="fas fa-music"></i>
              <span>Entertainment</span>
            </div>
          </div>
        </div>

        {/* Testimonials Card */}
        <div className="card testimonials-card">
          <h2>What Our Clients Say</h2>
          <div className="testimonials-container">
            <div className="testimonial">
              <div className="rating">★★★★★</div>
              <p>"Forever Invited made our event planning seamless and stress-free. The attention to detail was impeccable!"</p>
              <div className="author">- Sarah & James</div>
            </div>
            <div className="testimonial">
              <div className="rating">★★★★★</div>
              <p>"The platform is incredibly user-friendly and the team's support was outstanding throughout the process."</p>
              <div className="author">- Emily & Michael</div>
            </div>
            <div className="testimonial">
              <div className="rating">★★★★★</div>
              <p>"They turned our vision into reality. Every detail was perfectly executed!"</p>
              <div className="author">- Lisa & David</div>
            </div>
          </div>
        </div>

        {/* About Us Card */}
        <div className="card about-card">
          <h2>About Us</h2>
          <div className="about-content">
            <p>At Forever Invited, we believe every celebration deserves to be extraordinary. Our passion lies in creating unforgettable experiences through innovative planning solutions.</p>
            <p>With years of expertise in event planning and management, we offer a seamless blend of creativity, technology, and personal touch to make your special day truly memorable.</p>
            <div className="social-links">
              <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                <i className="fab fa-facebook-f"></i>
              </a>
              <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                <i className="fab fa-instagram"></i>
              </a>
              <a href="https://tiktok.com" target="_blank" rel="noopener noreferrer">
                <i className="fab fa-tiktok"></i>
              </a>
            </div>
          </div>
        </div>

        {/* Features Card */}
        <div className="card features-card">
          <h2>Why Choose Us</h2>
          <div className="features-grid">
            <div className="feature-item">
              <i className="fas fa-check-circle"></i>
              <span>Professional Planning</span>
            </div>
            <div className="feature-item">
              <i className="fas fa-clock"></i>
              <span>24/7 Support</span>
            </div>
            <div className="feature-item">
              <i className="fas fa-heart"></i>
              <span>Personalized Service</span>
            </div>
            <div className="feature-item">
              <i className="fas fa-star"></i>
              <span>Premium Experience</span>
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 Forever Invited. All Rights Reserved.</p>
      </footer>
    </div>
  );
};

export default HomePage; 