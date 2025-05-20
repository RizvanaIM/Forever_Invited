import React, { useState } from "react";
import { useNavigate } from "react-router-dom";  // Import useNavigate from React Router
import axios from "axios";  // Import Axios
import './FormStyles.css';  // Import the common form styles

const LoginPage = () => {
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate(); // Hook for navigation

  const handleChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Use Axios to send login request to backend
    axios
      .post("http://localhost:8080/api/login", loginData)
      .then((response) => {
        const user = response.data;
        localStorage.setItem("user", JSON.stringify(user));

        // Check the role and redirect based on it
        if (user.role === "Admin") {
          // Redirect to Admin Dashboard
          navigate("/admin-dashboard");
        } else if (user.role === "Service provider") {
          if (user.approved) {
            // Redirect to Service Provider Dashboard if approved
            navigate("/service-pro-d");
          } else {
            setError("Your account is awaiting admin approval.");
          }
        } else if (user.role === "Service seeker") {
          // Redirect to Service Seeker Dashboard
          navigate("/service-seeker-dashboard");
        }
      })
      .catch((err) => {
        setError("Invalid credentials. Please try again.");
        console.error(err);
      });
  };

  return (
    <div className="form-container">
      <h2>Login</h2>
      {error && <p className="error">{error}</p>} {/* Display error message */}
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          name="email"
          value={loginData.email}
          onChange={handleChange}
          placeholder="Email"
          required
        />
        <input
          type="password"
          name="password"
          value={loginData.password}
          onChange={handleChange}
          placeholder="Password"
          required
        />
        <button type="submit">Login</button>
        <a href="/forgotpassword" className="forgot-password-link">
          Forgot Password?
        </a>
      </form>
    </div>
  );
};

export default LoginPage;
