package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.Post;
import com.verb2bee.fullstack.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ค้นหาโพสต์ด้วยชื่อหัวข้อ (ตัวอย่าง Custom Query)
    List<Post> findByTitleContainingIgnoreCase(String title);

    // ใช้ JPQL เพื่อดึงข้อมูล Post พร้อม Comments ใน Query เดียว (Fetch Join)
    // ช่วยแก้ปัญหา N+1 Select Problem เมื่อต้องการดึงข้อมูลความสัมพันธ์ One-to-Many
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Post> findByIdWithComments(@Param("id") Long id);

    // ค้นหาโพสต์ที่มีจำนวนคอมเมนต์มากกว่าที่กำหนด
    @Query("SELECT p FROM Post p WHERE size(p.comments) > :count")
    List<Post> findPostsWithMoreThanComments(@Param("count") int count);

    // 1. Category: Bulk-Update
    // อัปเดตสถานะของโพสต์ตาม ID
    @Modifying
    @Transactional // สำคัญมาก: คำสั่งแก้ไขต้องรันภายใต้ Transaction
    @Query("UPDATE Post p SET p.status = :status WHERE p.id = :id")
    int updatePostStatus(@Param("id") Long id, @Param("status") PostStatus status);

    // 2. Category: Bulk-Delete
    // ลบโพสต์ที่มีคำที่กำหนดในเนื้อหา (Native SQL)
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM posts WHERE content LIKE %:keyword%", nativeQuery = true)
    void deletePostsByKeyword(@Param("keyword") String keyword);

    // 3. Category: Atomic Increment
    // เพิ่มยอดการเข้าชม (Views) โดยตรงใน Database
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);

}
