import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const GuestLogin = () => {
    const [email, setEmail] = useState('');
    const [secretKey, setSecretKey] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/guests/login', {
                email,
                secretKey
            });

            const guest = response.data;
            if (guest) {
                localStorage.setItem('guest', JSON.stringify(guest));
                setError('');
                navigate(`/ViewInvitation`);
            } else {
                setError('Invalid login or no invitation found.');
            }
        } catch (err) {
            setError('Login failed. Please check your credentials.');
        }
    };

    return (
        <div className="guest-login">
            <h2>Guest Login</h2>
            <form onSubmit={handleLogin}>
                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <label>Secret Key:</label>
                <input
                    type="text"
                    value={secretKey}
                    onChange={(e) => setSecretKey(e.target.value)}
                    required
                />

                <button type="submit">Login</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default GuestLogin;
