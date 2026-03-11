package com.verb2bee.fullstack.dto.response;

import java.util.List;

public class PostResponse {

    private Long id;
    private String title;
    private String content;

    // ใช้ List ของ CommentResponse แทนการใช้ Entity โดยตรง
    private List<CommentResponse> comments;

    // Default Constructor
    public PostResponse() {
    }

    // Constructor พร้อมข้อมูลครบถ้วน
    public PostResponse(Long id, String title, String content, List<CommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    // Getter และ Setter (Standard Java - No Lombok)
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

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
