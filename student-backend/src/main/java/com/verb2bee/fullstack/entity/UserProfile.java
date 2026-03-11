package com.verb2bee.fullstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    // mappedBy = "profile": ระบุว่าความสัมพันธ์นี้ถูกควบคุมโดยฟิลด์ 'profile' ในคลาส User
    @OneToOne(mappedBy = "profile")
    private User user;

    // No Lombok: ต้องเขียน Default Constructor
    public UserProfile() {
    }

    // Constructor สำหรับใช้งานทั่วไป
    public UserProfile(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getter และ Setter (Standard Java)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
