package com.verb2bee.fullstack.entity;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING) // เก็บเป็น 'ACTIVE', 'PENDING' ฯลฯ ใน MySQL
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.PENDING; // กำหนดค่าเริ่มต้น


    // One-to-One Relationship
    // cascade = CascadeType.ALL: ถ้าบันทึก/ลบ User ให้จัดการ UserProfile ด้วยอัตโนมัติ
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;

    // Default Constructor (จำเป็นสำหรับ JPA)
    public User() {
    }

    // Constructor พร้อมข้อมูล
    public User(String username, String email, UserProfile profile) {
        this.username = username;
        this.email = email;
        this.profile = profile;
    }

    // Getter และ Setter (เขียนแบบ Manual)
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

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}
