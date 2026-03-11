package com.verb2bee.fullstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    // Many-to-Many Relationship
    // mappedBy = "courses": อ้างอิงถึงชื่อตัวแปร 'courses' ในคลาส Student
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    // Default Constructor (Required by JPA)
    public Course() {
    }

    // Constructor พร้อมข้อมูล
    public Course(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
    }

    // Getter และ Setter

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
