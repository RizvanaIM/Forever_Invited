import React, { useState, useEffect } from 'react';
import Navbar from './Navbar';
import './HomePage.css';

const HomePage = () => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  const heroImages = [
    '/images/hero1.jpg',  
    '/images/hero2.jpg',  
    '/images/hero3.jpg',
    '/images/hero4.jpg',  
    '/images/hero5.jpg',  
    '/images/hero6.jpg',
    '/images/hero7.jpg',  
    '/images/hero8.jpg',  
    '/images/hero9.jpg',
    '/images/hero10.jpg',  
    '/images/hero11.jpg',  
    '/images/hero12.jpg',
    '/images/hero13.jpg',  
    '/images/hero14.jpg',  
    '/images/hero15.jpg'  
  ];

  useEffect(() => {
    // Image auto-change effect
    const imageInterval = setInterval(() => {
      setCurrentImageIndex((prevIndex) => 
        prevIndex === heroImages.length - 1 ? 0 : prevIndex + 1
      );
    }, 5000); // Change image every 5 seconds

    return () => {
      clearInterval(imageInterval);
    };
  }, []);

  return (
    <div className="homepage">
      <Navbar />

      {/* Hero Section */}
      <section className="hero">
        {heroImages.map((image, index) => (
          <div
            key={index}
            className={`hero-slide-${index} ${index === currentImageIndex ? 'active' : ''}`}
          />
        ))}
        <div className="hero-content">
          <h1 className="animated-title">
            <span className="title-forever">FOREVER</span>
            <span className="title-invited">INVITED</span>
          </h1>
          <p className="animated-subtitle">ALL IN ONE PLATFORM</p>
          <button className="cta-button">Choose Your Package</button>
        </div>
      </section>

      {/* Wedding Halls Section */}
      <section className="section wedding-halls">
        <h2>
          <span>WEDDING</span>
          <span>HALLS</span>
        </h2>
        <div className="image-grid">
          <div className="grid-item">
            <div className="hall-card">
              <img src="/images/hall1.jpg" alt="Wedding Hall" />
              <div className="hall-info">
                <h3>Grand Ballroom</h3>
                <p>Luxury wedding venue with elegant decor</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="hall-card">
              <img src="/images/hall2.jpg" alt="Wedding Hall" />
              <div className="hall-info">
                <h3>Royal Palace</h3>
                <p>Majestic venue for your special day</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="hall-card">
              <img src="/images/hall3.jpg" alt="Wedding Hall" />
              <div className="hall-info">
                <h3>Garden Paradise</h3>
                <p>Beautiful outdoor and indoor spaces</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="hall-card">
              <img src="/images/hall4.jpg" alt="Wedding Hall" />
              <div className="hall-info">
                <h3>Crystal Manor</h3>
                <p>Modern luxury with classic charm</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Event Planners Section */}
      <section className="section event-planners">
        <h2>
          <span>EVENT</span>
          <span>PLANNERS</span>
        </h2>
        <div className="image-grid">
          <div className="grid-item">
            <div className="planner-card">
              <img src="/images/planner1.jpg" alt="Event Planner" />
              <div className="planner-info">
                <h3>Elite Events</h3>
                <p>Luxury wedding planning & coordination</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="planner-card">
              <img src="/images/planner2.jpg" alt="Event Planner" />
              <div className="planner-info">
                <h3>Dream Weddings</h3>
                <p>Creating magical moments for couples</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="planner-card">
              <img src="/images/planner3.jpg" alt="Event Planner" />
              <div className="planner-info">
                <h3>Perfect Day</h3>
                <p>Full-service wedding planning</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="planner-card">
              <img src="/images/planner4.jpg" alt="Event Planner" />
              <div className="planner-info">
                <h3>Blissful Events</h3>
                <p>Making your wedding dreams reality</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Services Section */}
      <section className="section services">
        <h2>
          <span>SERVICES</span>
        </h2>
        <div className="image-grid">
          <div className="grid-item">
            <div className="service-card">
              <img src="/images/service1.jpg" alt="Service" />
              <div className="service-info">
                <h3>Photography</h3>
                <p>Professional wedding photography</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="service-card">
              <img src="/images/service2.jpg" alt="Service" />
              <div className="service-info">
                <h3>Catering</h3>
                <p>Exquisite wedding catering services</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="service-card">
              <img src="/images/service3.jpg" alt="Service" />
              <div className="service-info">
                <h3>Decoration</h3>
                <p>Beautiful wedding decorations</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
          <div className="grid-item">
            <div className="service-card">
              <img src="/images/service4.jpg" alt="Service" />
              <div className="service-info">
                <h3>Entertainment</h3>
                <p>Music and entertainment services</p>
                <button className="view-details">View Details</button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Testimonials Section */}
      <section className="testimonials">
        <h2>
          <span>WHAT OUR</span>
          <span>COUPLES SAY</span>
        </h2>
        <div className="testimonials-grid">
          <div className="testimonial-card">
            <div className="rating">
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
            </div>
            <h3>Dream Wedding Come True</h3>
            <p className="review-text">
              "Forever Invited made our wedding planning journey absolutely magical. Their attention to detail and professional service exceeded all our expectations. The venue suggestions were perfect!"
            </p>
            <div className="user-info">
              <img src="/images/service2.jpg" alt="Sarah & James" />
              <span>Sarah & James</span>
            </div>
          </div>

          <div className="testimonial-card">
            <div className="rating">
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
            </div>
            <h3>Stress-Free Planning</h3>
            <p className="review-text">
              "The platform is so intuitive and user-friendly. We found our perfect planner and venue all in one place. Forever Invited truly made our wedding planning stress-free and enjoyable!"
            </p>
            <div className="user-info">
              <img src="/images/service2.jpg" alt="Emily & Michael" />
              <span>Emily & Michael</span>
            </div>
          </div>

          <div className="testimonial-card">
            <div className="rating">
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
              <i className="fas fa-star"></i>
            </div>
            <h3>Exceptional Service</h3>
            <p className="review-text">
              "From start to finish, Forever Invited provided exceptional service. The vendors they connected us with were top-notch, and their support team was always there when we needed them."
            </p>
            <div className="user-info">
              <img src="/images/service2.jpg" alt="Lisa & David" />
              <span>Lisa & David</span>
            </div>
          </div>
        </div>
      </section>

      {/* About Us Section */}
      <section className="about-us" id="about">
        <h2>
          <span>ABOUT</span>
          <span>US</span>
        </h2>
        <p>
          At <span className="highlight">Forever Invited</span>, we believe that every love story deserves a perfect celebration. 
          Our passion lies in transforming wedding dreams into seamless realities through our innovative platform. 
          We seamlessly blend timeless elegance with modern technology, offering a curated experience that brings together 
          exceptional venues, talented planners, and premium services. Our commitment is to make your wedding journey as 
          beautiful and memorable as your special day, providing a sophisticated, stress-free planning experience for 
          couples, venue owners, and service providers alike.
        </p>
        <div className="social-links">
          <a href="https://facebook.com/foreverinvited" target="_blank" rel="noopener noreferrer" aria-label="Facebook">
            <i className="fab fa-facebook-f"></i>
          </a>
          <a href="https://instagram.com/foreverinvited" target="_blank" rel="noopener noreferrer" aria-label="Instagram">
            <i className="fab fa-instagram"></i>
          </a>
          <a href="https://tiktok.com/@foreverinvited" target="_blank" rel="noopener noreferrer" aria-label="TikTok">
            <i className="fab fa-tiktok"></i>
          </a>
          <a href="mailto:contact@foreverinvited.com" aria-label="Email">
            <i className="fas fa-envelope"></i>
          </a>
        </div>
        <div className="copyright">
          <div className="copyright-text">
            <span>Copyrights 2025 - Forever Invited Team. All Rights Reserved.</span>
          </div>
          <div className="design-credit">
            <span>Design + Development by Forever Invited Group</span>
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage; 