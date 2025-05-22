package com.foreverinvited.model;

import jakarta.persistence.*;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String nameOfPhotographer;  // Only for Wedding Photography
    private String serviceType;
    private Double price;
    private Double advancePayment;
    private String description;
    private String bookingConditions;

    // Wedding Hall attributes
    private String hallName;
    private String hallAddress;
    private Integer hallCapacity;

    @ManyToOne
    @JoinColumn(name = "user_email") // Assuming the User entity is correctly mapped
    private User user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNameOfPhotographer() {
        return nameOfPhotographer;
    }

    public void setNameOfPhotographer(String nameOfPhotographer) {
        this.nameOfPhotographer = nameOfPhotographer;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(Double advancePayment) {
        this.advancePayment = advancePayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookingConditions() {
        return bookingConditions;
    }

    public void setBookingConditions(String bookingConditions) {
        this.bookingConditions = bookingConditions;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getHallAddress() {
        return hallAddress;
    }

    public void setHallAddress(String hallAddress) {
        this.hallAddress = hallAddress;
    }

    public Integer getHallCapacity() {
        return hallCapacity;
    }

    public void setHallCapacity(Integer hallCapacity) {
        this.hallCapacity = hallCapacity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
