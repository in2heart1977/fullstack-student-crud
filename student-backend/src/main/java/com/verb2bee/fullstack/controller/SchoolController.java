package com.verb2bee.fullstack.controller;

import com.verb2bee.fullstack.dto.request.StudentRequest;
import com.verb2bee.fullstack.dto.request.CourseRequest;
import com.verb2bee.fullstack.dto.response.StudentResponse;
import com.verb2bee.fullstack.dto.response.CourseResponse;
import com.verb2bee.fullstack.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {

    private final SchoolService schoolService;

    // Constructor Injection (No @Autowired)
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // สร้างข้อมูลนักเรียนใหม่
    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = schoolService.createStudent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // สร้างข้อมูลวิชาเรียนใหม่
    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse response = schoolService.createCourse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ลงทะเบียนนักเรียนเข้าสู่วิชาเรียน (Many-to-Many Enrollment)
    // ใช้ PathVariable เพื่อรับ ID ของทั้งสองฝั่งมาจับคู่กันในตารางกลาง
    @PostMapping("/students/{studentId}/enroll/{courseId}")
    public ResponseEntity<StudentResponse> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        StudentResponse response = schoolService.enrollStudentToCourse(studentId, courseId);
        return ResponseEntity.ok(response);
    }

    // ดึงข้อมูลนักเรียนทั้งหมด พร้อมวิชาที่แต่ละคนลงทะเบียน
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> responses = schoolService.getAllStudents();
        return ResponseEntity.ok(responses);
    }
}
