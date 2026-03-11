package com.verb2bee.fullstack.dto.response;

import java.time.LocalDate;
import java.util.Set;

public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate birthDate;

    // แสดงรายการวิชาที่นักเรียนคนนี้ลงทะเบียน (Many-to-Many)
    private Set<CourseResponse> courses;

    // Default Constructor (จำเป็นสำหรับ JSON Mapping)
    public StudentResponse() {
    }

    // Constructor พร้อมข้อมูลครบถ้วน
    public StudentResponse(Long id, String name, String email, Integer age, LocalDate birthDate, Set<CourseResponse> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        this.courses = courses;
    }

    // Getter และ Setter (No Lombok)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseResponse> courses) {
        this.courses = courses;
    }
}
