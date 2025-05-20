import React, { useState } from "react";
import axios from "axios";  // Import Axios
import './FormStyles.css';

const ForgotPasswordPage = () => {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // API call to send the password reset token
    axios
      .post(`http://localhost:8080/api/forgot-password?email=${email}`)
      .then((response) => {
        setMessage("Password reset email sent successfully!");
      })
      .catch((err) => {
        setMessage("Failed to send reset email. User not found.");
        console.error(err);
      });
  };

  return (
    <div className="forgot-password-form">
      <h2>Forgot Password</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          value={email}
          onChange={handleChange}
          placeholder="Enter your email"
          required
        />
        <button type="submit">Send Reset Link</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default ForgotPasswordPage;
