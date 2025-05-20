import React, { useState } from "react";
import axios from "axios";  // Import Axios
import './FormStyles.css';

const ResetPasswordPage = () => {
  const [newPassword, setNewPassword] = useState("");
  const [token, setToken] = useState("");  // Reset token from the URL or user input
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // Send the new password and reset token to backend
    axios
      .post(
        `http://localhost:8080/api/reset-password?token=${token}&newPassword=${newPassword}`
      )
      .then((response) => {
        setMessage("Password reset successfully!");
      })
      .catch((err) => {
        setMessage("Failed to reset password. Invalid or expired token.");
        console.error(err);
      });
  };

  return (
    <div className="reset-password-form">
      <h2>Reset Password</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={token}
          onChange={(e) => setToken(e.target.value)}
          placeholder="Enter reset token"
          required
        />
        <input
          type="password"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
          placeholder="Enter new password"
          required
        />
        <button type="submit">Reset Password</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default ResetPasswordPage;
