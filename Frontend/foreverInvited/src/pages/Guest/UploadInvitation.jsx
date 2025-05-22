import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const UploadInvitation = () => {
  const [message, setMessage] = useState('');
  const [date, setDate] = useState('');
  const [image, setImage] = useState(null);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    if (!user) {
      alert('You must be logged in.');
      navigate('/login');
    }
  }, [navigate, user]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('message', message);
    formData.append('date', date);
    formData.append('image', image);
    formData.append('email', user.email); // include user email

    try {
      await axios.post('http://localhost:8080/api/invitation/upload', formData);
      setStatus('Invitation uploaded successfully.');
    } catch (error) {
      setStatus('Upload failed.');
    }
  };

  return (
    <div>
      <h2>Upload Invitation</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Invitation Message"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          required
        />
        <input
          type="date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
          required
        />
        <input
          type="file"
          accept="image/*"
          onChange={(e) => setImage(e.target.files[0])}
          required
        />
        <button type="submit">Upload</button>
      </form>
      <p>{status}</p>
    </div>
  );
};

export default UploadInvitation;
