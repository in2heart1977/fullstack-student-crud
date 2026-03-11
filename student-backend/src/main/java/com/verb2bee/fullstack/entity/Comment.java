package com.verb2bee.fullstack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Embedded // ดึงฟิลด์ createdAt และ updatedAt จาก AuditMetadata มาใส่ในตาราง posts
    private AuditMetadata audit = new AuditMetadata();

    // Many-to-One Relationship
    // fetch = FetchType.LAZY: ดึงข้อมูล Post เมื่อต้องการใช้งานจริงเท่านั้น (ช่วยเรื่อง Performance)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false) // ระบุ Foreign Key ใน MySQL
    private Post post;

    // Default Constructor (สำหรับ JPA)
    public Comment() {
    }

    // Constructor พร้อมข้อมูล
    public Comment(String text) {
        this.text = text;
    }

    // Getter และ Setter (Standard Java)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public AuditMetadata getAudit() {
        return audit;
    }

    public void setAudit(AuditMetadata audit) {
        this.audit = audit;
    }

}
