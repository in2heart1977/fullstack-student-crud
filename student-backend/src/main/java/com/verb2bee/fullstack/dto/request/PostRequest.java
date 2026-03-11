package com.verb2bee.fullstack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {

    @NotBlank(message = "Post title cannot be empty")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 2000, message = "Content cannot exceed 2000 characters")
    private String content;

    // No Lombok: Default Constructor
    public PostRequest() {
    }

    // Constructor พร้อมข้อมูล
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter และ Setter (Standard Java)
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
}
