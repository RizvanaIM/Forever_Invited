import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import RegistrationPage from "./pages/HOME/RegistrationPage";
import LoginPage from "./pages/HOME/LoginPage";
import ForgotPasswordPage from "./pages/HOME/ForgotPasswordPage";
import ResetPasswordPage from "./pages/HOME/ResetPasswordPage";
import AddService from "./pages/SERVICE PROVIDER/AddService";
import ServicePage from "./pages/SERVICE PROVIDER/ServicePage";
import ServiceProDashboard from "./pages/SERVICE PROVIDER/ServiceProDashboard"
import ChooseYourPlan from "./pages/SERVICE SEEKER/ChooseYourPlan" ;

import GuestManagement from "./pages/Guest/GuestManagement" ;
import GuestLogin from "./pages/Guest/GuestLogin" ;

import ViewInvitation from "./pages/Guest/ViewInvitation" ;
import UploadInvitation from "./pages/Guest/UploadInvitation" ;
import AdminDashboardPage from "./pages/ADMIN/AdminDashboardPage";
import ViewCart from "./pages/SERVICE SEEKER/ViewCart";
import ServiceSeekerDashboard from "./pages/SERVICE SEEKER/ServiceSeekerDashboard";
import ProviderBookings from "./pages/SERVICE PROVIDER/ProviderBookings";
import HomePage from "./components/HomePage"

function App() {
  return (
    <Router>
      <Routes>
        {/* Define your route path and the component */}
        <Route path="/home" element={<HomePage />} />
        <Route path="/register" element={<RegistrationPage />} />
        <Route path="/login" element={<LoginPage/>} />
        <Route path="/forgotpassword" element={<ForgotPasswordPage/>} />
        <Route path="/resetPassword" element={<ResetPasswordPage/>} />

        <Route path="/addservice" element={<AddService/>} />
        <Route path="/services" element={<ServicePage/>} />
        <Route path="/service-pro-d" element={<ServiceProDashboard/>} />
        <Route path="/chooseplan" element={<ChooseYourPlan/>} />

        
        <Route path="/guestManagement" element={<GuestManagement/>} />
        <Route path="/GuestLogin" element={<GuestLogin/>} />

        <Route path="/ViewInvitation" element={<ViewInvitation/>} />
        <Route path="/UploadInvitation" element={<UploadInvitation/>} />
        <Route path="/admin-dashboard" element={<AdminDashboardPage/>} />


        <Route path="/service-seeker-dashboard/view-cart" element={<ViewCart/>} />

        <Route path="/service-seeker-dashboard" element={<ServiceSeekerDashboard/>} />

        <Route path="/ProviderBookings" element={<ProviderBookings/>} />
        
        

      </Routes>
    </Router>
  );
}

export default App;
