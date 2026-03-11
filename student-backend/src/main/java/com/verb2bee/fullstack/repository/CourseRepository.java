package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // ค้นหาวิชาด้วยรหัสวิชา (Unique Key เช่น CS101)
    Optional<Course> findByCourseCode(String courseCode);

    // ค้นหาวิชาทั้งหมดที่มีชื่อวิชาคล้ายกับที่ระบุ
    List<Course> findByTitleContainingIgnoreCase(String title);

    // ดึงข้อมูล Course พร้อมรายชื่อนักเรียนที่ลงทะเบียน (Many-to-Many Fetch)
    // เพื่อป้องกัน LazyInitializationException เมื่อเรียกดูรายชื่อนักเรียน
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id = :id")
    Optional<Course> findByIdWithStudents(@Param("id") Long id);

    // ค้นหาวันวิชาเรียนทั้งหมดที่นักเรียนคนนี้ลงทะเบียน (ค้นหาข้ามตารางกลาง)
    List<Course> findByStudentsId(Long studentId);

    // ตรวจสอบว่ามีรหัสวิชานี้อยู่ในระบบแล้วหรือไม่
    boolean existsByCourseCode(String courseCode);
}
