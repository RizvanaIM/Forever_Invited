import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ViewInvitation = () => {
  const [invitations, setInvitations] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const guest = JSON.parse(localStorage.getItem('guest'));
    if (!guest) {
      alert('You must be logged in.');
      navigate('/guest/login');
      return;
    }

    axios.get(`http://localhost:8080/api/guests/${guest.id}/invitation`)
      .then(res => {
        const data = res.data;
        if (Array.isArray(data)) {
          setInvitations(data);
        } else {
          setInvitations([data]); // fallback if backend returns single object
        }
      })
      .catch(err => {
        console.error(err);
        alert("Failed to load invitation(s).");
      })
      .finally(() => setLoading(false));
  }, [navigate]);

  if (loading) return <p>Loading...</p>;
  if (!invitations.length) return <p>No invitations found.</p>;

  return (
    <div className="invitation-card">
      <h2>You Are Invited!</h2>
      {invitations.map((inv, idx) => (
        <div key={idx} className="invitation-detail" style={{ border: '1px solid #ccc', padding: 10, marginBottom: 20 }}>
          <p><strong>Message:</strong> {inv.invitationMessage}</p>
          <p><strong>Wedding Date:</strong> {inv.weddingDate}</p>
          {inv.invitationImageUrl && (
            <img
              src={inv.invitationImageUrl}
              alt="Wedding Invitation"
              style={{ maxWidth: '100%', height: 'auto', borderRadius: 8 }}
            />
          )}
        </div>
      ))}
    </div>
  );
};

export default ViewInvitation;
