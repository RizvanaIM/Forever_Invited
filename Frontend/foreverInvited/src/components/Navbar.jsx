import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const navigate = useNavigate();

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 50);
    };

    const handleResize = () => {
      setWindowWidth(window.innerWidth);
      if (window.innerWidth > 768) {
        setIsMenuOpen(false);
      }
    };

    window.addEventListener('scroll', handleScroll);
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('scroll', handleScroll);
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
    if (!isMenuOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'unset';
    }
  };

  const closeMenu = () => {
    setIsMenuOpen(false);
    document.body.style.overflow = 'unset';
  };

  const handleLogin = () => {
    closeMenu();
    navigate('/login');
  };

  return (
    <header className={`header ${isScrolled ? 'scrolled' : ''}`}>
      <div className="logo-container">
        <a href="#home" onClick={closeMenu}>
          <img src="/images/ForeverInvited.png" alt="Forever Invited" className="logo-img" />
        </a>
      </div>
      <div className={`menu-icon ${isMenuOpen ? 'active' : ''}`} onClick={toggleMenu}>
        <div></div>
        <div></div>
        <div></div>
      </div>
      <nav className={isMenuOpen ? 'active' : ''}>
        <div className="nav-links">
          <a href="#testimonials" onClick={closeMenu}>TESTIMONIALS</a>
          <a href="#about" onClick={closeMenu}>ABOUT US</a>
          <a href="#need" onClick={closeMenu}>NEED</a>
        </div>
        <div className="nav-buttons">
          <button className="login-btn" onClick={handleLogin}>LOGIN</button>
          <button className="signup-btn" onClick={closeMenu}>SIGN UP</button>
        </div>
      </nav>
    </header>
  );
};

export default Navbar; 