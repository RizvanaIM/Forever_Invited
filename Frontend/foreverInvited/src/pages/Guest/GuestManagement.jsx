import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const GuestManagement = () => {
    const navigate = useNavigate();
    const [guests, setGuests] = useState([]);
    const [guest, setGuest] = useState({ name: '', email: '' });
    const [editingGuestId, setEditingGuestId] = useState(null);
    const [editingGuest, setEditingGuest] = useState({ name: '', email: '' });

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
            alert('You must be logged in.');
            navigate('/login');
            return;
        }

        fetchGuests(user.email);
    }, [navigate]);

    const fetchGuests = (userEmail) => {
        axios.get(`http://localhost:8080/api/guests/?userEmail=${userEmail}`)
            .then(response => setGuests(response.data))
            .catch(error => console.error('Error fetching guests:', error));
    };

    const handleAddGuest = (e) => {
        e.preventDefault();

        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
            alert('You must be logged in.');
            navigate('/login');
            return;
        }

        const guestWithUser = {
            ...guest,
            user: { email: user.email }  // Pass user for foreign key
        };

        axios.post('http://localhost:8080/api/guests/add', guestWithUser)
            .then(response => {
                setGuests([...guests, response.data]);
                setGuest({ name: '', email: '' });
            })
            .catch(error => console.error('Error adding guest:', error));
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/api/guests/delete/${id}`)
            .then(() => setGuests(guests.filter(g => g.id !== id)))
            .catch(error => console.error('Error deleting guest:', error));
    };

    const handleSendInvitation = (id) => {
        axios.post(`http://localhost:8080/api/guests/send-invitation/${id}`)
            .then(res => alert(res.data))
            .catch(err => alert("Failed to send invitation"));
    };

    const startEditing = (guest) => {
        setEditingGuestId(guest.id);
        setEditingGuest({ name: guest.name, email: guest.email });
    };

    const handleUpdate = (id) => {
        axios.put(`http://localhost:8080/api/guests/update/${id}`, editingGuest)
            .then(response => {
                const updatedList = guests.map(g => g.id === id ? response.data : g);
                setGuests(updatedList);
                setEditingGuestId(null);
                setEditingGuest({ name: '', email: '' });
            })
            .catch(error => console.error('Error updating guest:', error));
    };

    return (
        <div>
            <h2>Guest Management</h2>
            <form onSubmit={handleAddGuest}>
                <input
                    type="text"
                    placeholder="Name"
                    value={guest.name}
                    onChange={(e) => setGuest({ ...guest, name: e.target.value })}
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={guest.email}
                    onChange={(e) => setGuest({ ...guest, email: e.target.value })}
                    required
                />
                <button type="submit">Add Guest</button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {guests.map(g => (
                        <tr key={g.id}>
                            <td>
                                {editingGuestId === g.id ? (
                                    <input
                                        type="text"
                                        value={editingGuest.name}
                                        onChange={(e) =>
                                            setEditingGuest({ ...editingGuest, name: e.target.value })
                                        }
                                    />
                                ) : (
                                    g.name
                                )}
                            </td>
                            <td>
                                {editingGuestId === g.id ? (
                                    <input
                                        type="email"
                                        value={editingGuest.email}
                                        onChange={(e) =>
                                            setEditingGuest({ ...editingGuest, email: e.target.value })
                                        }
                                    />
                                ) : (
                                    g.email
                                )}
                            </td>
                            <td>
                                <button onClick={() => handleSendInvitation(g.id)}>Send</button>
                                <button onClick={() => handleDelete(g.id)}>Delete</button>
                                {editingGuestId === g.id ? (
                                    <button onClick={() => handleUpdate(g.id)}>Save</button>
                                ) : (
                                    <button onClick={() => startEditing(g)}>Update</button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default GuestManagement;
