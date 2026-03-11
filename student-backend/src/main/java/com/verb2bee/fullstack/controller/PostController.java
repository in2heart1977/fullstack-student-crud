package com.verb2bee.fullstack.controller;

import com.verb2bee.fullstack.dto.request.PostRequest;
import com.verb2bee.fullstack.dto.request.CommentRequest;
import com.verb2bee.fullstack.dto.response.PostResponse;
import com.verb2bee.fullstack.dto.response.CommentResponse;
import com.verb2bee.fullstack.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // Constructor Injection (No @Autowired)
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // สร้างโพสต์ใหม่ (One-to-Many Owner)
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse response = postService.createPost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // เพิ่มคอมเมนต์ลงในโพสต์ (Many-to-One Link)
    // ใช้ CommentRequest ที่มี postId อยู่ข้างใน
    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest request) {
        CommentResponse response = postService.addCommentToPost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ดึงข้อมูลโพสต์ทั้งหมด พร้อมคอมเมนต์ของแต่ละโพสต์
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    // ดึงข้อมูลโพสต์เดียวตาม ID (จะดึง Comments ทั้งหมดที่ผูกอยู่มาด้วย)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
}
