package com.verb2bee.fullstack.dto.response;

public class UserResponse {

    private Long id;
    private String username;
    private String email;

    // ดึงข้อมูลจาก UserProfile มาใส่ในนี้โดยตรง (Flat DTO)
    // หรือจะแยกเป็นอีก Object ก็ได้ แต่แบบนี้จะใช้ง่ายกว่าในฝั่ง Frontend
    private String address;
    private String phoneNumber;

    // Default Constructor (จำเป็นสำหรับ JSON Mapping)
    public UserResponse() {
    }

    // Constructor สำหรับรับข้อมูลครบถ้วน
    public UserResponse(Long id, String username, String email, String address, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getter และ Setter (No Lombok)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
