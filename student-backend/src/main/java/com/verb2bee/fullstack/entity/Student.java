package com.verb2bee.fullstack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Many-to-Many Relationship
    // @JoinTable: กำหนดชื่อตารางกลาง 'student_courses' และคอลัมน์ที่ใช้เชื่อมโยง
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date lastLogin;

    // --- 1. Calculated-Data Category ---
    @Transient // Hibernate จะไม่สร้างคอลัมน์ 'age' ใน MySQL
    private Integer age;

    @Column(nullable = false)
    private Double gpa;

    // --- 2. UI-State-Flag Category ---
    @Transient // ใช้เช็คสถานะใน Memory เท่านั้น ไม่ต้องลง DB
    private boolean isNewlyRegistered;

    // Logic ในการคำนวณอายุ (Business Logic ใน Entity)
    @PostLoad // ให้ทำงานทันทีหลังจากดึงข้อมูลจาก Database เสร็จ
    public void calculateAge() {
        if (birthDate != null) {
            this.age = Period.between(birthDate, LocalDate.now()).getYears();
        }
    }

    // Default Constructor
    public Student() {
    }

    // Constructor พร้อมข้อมูล
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter และ Setter
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    // Helper Methods สำหรับจัดการความสัมพันธ์ Many-to-Many
    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isNewlyRegistered() {
        return isNewlyRegistered;
    }

    public void setNewlyRegistered(boolean newlyRegistered) {
        isNewlyRegistered = newlyRegistered;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
