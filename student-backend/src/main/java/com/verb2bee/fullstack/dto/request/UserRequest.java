package com.verb2bee.fullstack.dto.request;

import jakarta.validation.constraints.*;

import java.util.Map;

public class UserRequest {

    // --- ส่วนของ User ---
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // --- ส่วนของ UserProfile ---
    @NotNull(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    // Default Constructor
    public UserRequest() {
    }

    // Constructor พร้อมข้อมูล
    public UserRequest(String username, String email, String address, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getter และ Setter (No Lombok)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
