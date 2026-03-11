package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ค้นหาคอมเมนต์ทั้งหมดที่อยู่ใน Post เดียวกัน (Many-to-One)
    // Spring Data JPA จะเข้าใจโดยอัตโนมัติจากการตั้งชื่อ findBy + [ชื่อฟิลด์ใน Entity] + [ชื่อฟิลด์ใน Parent]
    List<Comment> findByPostId(Long postId);

    // ค้นหาคอมเมนต์ที่มีข้อความ (Text) ตามที่ระบุ
    List<Comment> findByTextContainingIgnoreCase(String text);

    // ลบคอมเมนต์ทั้งหมดที่ผูกกับ Post ID นั้นๆ
    void deleteByPostId(Long postId);
}
