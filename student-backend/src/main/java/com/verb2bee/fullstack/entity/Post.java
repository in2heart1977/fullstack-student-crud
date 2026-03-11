package com.verb2bee.fullstack.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    // --- Category: Creation-Tracking ---
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Category: Modification-Tracking ---
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Lob // บอก Hibernate ว่าเนื้อหานี้อาจจะยาวหลายหมื่นตัวอักษร
    @Column(name = "content", columnDefinition = "LONGTEXT") // เจาะจงประเภทใน MySQL
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status = PostStatus.DRAFT;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    // One-to-Many Relationship
    // mappedBy = "post": ระบุว่าความสัมพันธ์นี้ถูกควบคุมโดยฟิลด์ 'post' ในคลาส Comment
    // cascade = CascadeType.ALL: เมื่อลบ Post จะลบ Comments ทั้งหมดที่เกี่ยวข้องด้วย
    // orphanRemoval = true: เมื่อลบ Comment ออกจาก List จะลบออกจาก Database ด้วย
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Default Constructor
    public Post() {
    }

    // Constructor พร้อมข้อมูล
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter และ Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Helper Method สำหรับเพิ่ม Comment (แนะนำให้มีใน Bidirectional)
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
