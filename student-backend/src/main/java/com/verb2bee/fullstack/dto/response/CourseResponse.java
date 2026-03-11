package com.verb2bee.fullstack.dto.response;

public class CourseResponse {

    private Long id;
    private String courseCode;
    private String title;

    // Default Constructor (จำเป็นสำหรับ JSON Mapping)
    public CourseResponse() {
    }

    // Constructor พร้อมข้อมูลครบถ้วน
    public CourseResponse(Long id, String courseCode, String title) {
        this.id = id;
        this.courseCode = courseCode;
        this.title = title;
    }

    // Getter และ Setter (No Lombok)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
