package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // ค้นหานักเรียนด้วยอีเมล (Unique Key)
    Optional<Student> findByEmail(String email);

    // ตรวจสอบว่ามีอีเมลนี้ในระบบหรือไม่
    boolean existsByEmail(String email);

    // ดึงข้อมูล Student พร้อมกับรายการ Course ทั้งหมดที่ลงทะเบียน (Many-to-Many Fetch)
    // การใช้ JOIN FETCH ช่วยลดปัญหา N+1 Query ในความสัมพันธ์ Many-to-Many
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :id")
    Optional<Student> findByIdWithCourses(@Param("id") Long id);

    // ค้นหานักเรียนทั้งหมดที่ลงเรียนในวิชาที่ระบุ (ค้นหาข้ามตารางกลาง)
    List<Student> findByCoursesId(Long courseId);

    // ค้นหานักเรียนที่มีชื่อคล้ายกับที่ระบุ
    List<Student> findByNameContainingIgnoreCase(String name);
}
