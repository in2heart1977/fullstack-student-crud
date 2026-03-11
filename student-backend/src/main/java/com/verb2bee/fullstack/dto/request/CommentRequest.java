package com.verb2bee.fullstack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CommentRequest {

    @NotNull(message = "Post ID is mandatory")
    @Positive(message = "Invalid Post ID")
    private Long postId;

    @NotBlank(message = "Comment text cannot be empty")
    @Size(max = 500, message = "Comment must not exceed 500 characters")
    private String text;

    // Default Constructor (สำหรับ Jackson)
    public CommentRequest() {
    }

    // Constructor พร้อมข้อมูล
    public CommentRequest(Long postId, String text) {
        this.postId = postId;
        this.text = text;
    }

    // Getter และ Setter (No Lombok)
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
