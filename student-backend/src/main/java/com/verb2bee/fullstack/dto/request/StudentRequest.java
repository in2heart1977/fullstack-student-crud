package com.verb2bee.fullstack.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudentRequest {

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Student must be at least 18 years old")
    @Max(value = 60, message = "Student age cannot exceed 60")
    private Integer age;

    @Digits(integer = 1, fraction = 2, message = "GPA must be between 0.00 and 4.00")
    private Double gpa; // รองรับ x.xx เช่น 3.75

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    // No Lombok: Default Constructor
    public StudentRequest() {
    }

    // Constructor พร้อมข้อมูล
    public StudentRequest(String name, String email, Integer age, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
    }

    // Getter และ Setter (Standard Java)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
