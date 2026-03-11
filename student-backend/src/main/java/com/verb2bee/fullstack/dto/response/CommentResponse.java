package com.verb2bee.fullstack.dto.response;

public class CommentResponse {

    private Long id;
    private String text;

    // Default Constructor
    public CommentResponse() {
    }

    // Constructor พร้อมข้อมูล
    public CommentResponse(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    // Getter และ Setter (No Lombok)
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
}
