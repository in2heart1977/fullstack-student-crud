package com.verb2bee.fullstack.service;

import com.verb2bee.fullstack.dto.request.StudentRequest;
import com.verb2bee.fullstack.dto.request.CourseRequest;
import com.verb2bee.fullstack.dto.response.StudentResponse;
import com.verb2bee.fullstack.dto.response.CourseResponse;
import com.verb2bee.fullstack.entity.Student;
import com.verb2bee.fullstack.entity.Course;
import com.verb2bee.fullstack.repository.StudentRepository;
import com.verb2bee.fullstack.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SchoolService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Constructor Injection (No @Autowired)
    public SchoolService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // --- Student Operations ---

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());

        Student savedStudent = studentRepository.save(student);
        return mapToStudentResponse(savedStudent);
    }

    // --- Course Operations ---

    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }

        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setTitle(request.getTitle());

        Course savedCourse = courseRepository.save(course);
        return mapToCourseResponse(savedCourse);
    }

    // --- Many-to-Many Enrollment Logic ---

    @Transactional
    public StudentResponse enrollStudentToCourse(Long studentId, Long courseId) {
        // 1. ค้นหา Student และ Course จาก DB
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 2. เพิ่มวิชาเข้าไปในรายการของนักเรียน (ใช้ Helper Method ใน Student Entity)
        student.addCourse(course);

        // 3. บันทึก (JPA จะจัดการตารางกลาง student_courses ให้เองอัตโนมัติ)
        Student updatedStudent = studentRepository.save(student);

        return mapToStudentResponse(updatedStudent);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student : students) {
            responses.add(mapToStudentResponse(student));
        }
        return responses;
    }

    // --- Manual Mappers ---

    private StudentResponse mapToStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());

        // Mapping Set ของ Course (Entity) เป็น Set ของ CourseResponse (DTO)
        Set<CourseResponse> courseDtos = new HashSet<>();
        if (student.getCourses() != null) {
            for (Course course : student.getCourses()) {
                courseDtos.add(mapToCourseResponse(course));
            }
        }
        response.setCourses(courseDtos);

        return response;
    }

    private CourseResponse mapToCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseCode(course.getCourseCode());
        response.setTitle(course.getTitle());
        return response;
    }
}
