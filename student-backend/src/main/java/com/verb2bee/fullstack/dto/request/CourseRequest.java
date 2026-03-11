package com.verb2bee.fullstack.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CourseRequest {

    @NotBlank(message = "Course code is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}$", message = "Course code must be 2 uppercase letters followed by 3 digits (e.g., CS101)")
    private String courseCode;

    @NotNull(message = "Start date is required")
    @Future(message = "The course start date must be a future date")
    private LocalDate startDate;

    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    // No Lombok: Default Constructor
    public CourseRequest() {
    }

    // Constructor พร้อมข้อมูล
    public CourseRequest(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
    }

    // Getter และ Setter (Standard Java)
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
