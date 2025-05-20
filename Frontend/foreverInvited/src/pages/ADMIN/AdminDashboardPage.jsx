import React, { useState, useEffect } from "react";
import './AdminDashboardPage.css';

const AdminDashboardPage = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    // Fetch all users on page load
    fetch("http://localhost:8080/api/admin/users")
      .then((response) => response.json())
      .then((data) => setUsers(data))
      .catch((err) => setError("Failed to load users"));
  }, []);

  const handleApprove = (email) => {
    fetch(`http://localhost:8080/api/admin/approve?email=${email}`, {
      method: "POST",
    })
      .then((response) => response.text())
      .then((message) => {
        if (message.includes("approved")) {
          setUsers(users.map(user => 
            user.email === email ? { ...user, approved: true } : user
          ));
        } else {
          setError("Failed to approve user.");
        }
      });
  };

  const handleReject = (email) => {
    fetch(`http://localhost:8080/api/admin/reject?email=${email}`, {
      method: "POST",
    })
      .then((response) => response.text())
      .then((message) => {
        if (message.includes("rejected")) {
          setUsers(users.filter(user => user.email !== email));
        } else {
          setError("Failed to reject user.");
        }
      });
  };

  return (
    <div className="admin-dashboard">
      <h2>Admin Dashboard</h2>
      {error && <p className="error">{error}</p>}
      <table>
        <thead>
          <tr>
            <th>Email</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Role</th>
            <th>Approved</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.email}>
              <td>{user.email}</td>
              <td>{user.name || '-'}</td>
              <td>{user.phone || '-'}</td>
              <td>{user.role || '-'}</td>
              <td>{user.approved ? "Approved" : "Pending"}</td>
              <td>
                {!user.approved && (user.role === "Service provider" || user.role === "Admin") && (
                  <>
                    <button onClick={() => handleApprove(user.email)}>Approve</button>
                    <button onClick={() => handleReject(user.email)}>Reject</button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminDashboardPage;
