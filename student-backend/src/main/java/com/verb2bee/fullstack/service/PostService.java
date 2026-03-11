package com.verb2bee.fullstack.service;

import com.verb2bee.fullstack.dto.request.PostRequest;
import com.verb2bee.fullstack.dto.request.CommentRequest;
import com.verb2bee.fullstack.dto.response.PostResponse;
import com.verb2bee.fullstack.dto.response.CommentResponse;
import com.verb2bee.fullstack.entity.Post;
import com.verb2bee.fullstack.entity.Comment;
import com.verb2bee.fullstack.repository.PostRepository;
import com.verb2bee.fullstack.repository.CommentRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Constructor Injection (No @Autowired)
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // --- Post CRUD Operations ---

    @Transactional
    public PostResponse createPost(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : posts) {
            responses.add(mapToPostResponse(post));
        }
        return responses;
    }

    @Transactional(readOnly = true)
    // ตรวจสอบว่า ID ที่ส่งมาต้องไม่เป็น null และต้องมากกว่า 0 เสมอ
    public PostResponse getPostById(@NotNull @Min(1) Long id) {
        Post post = postRepository.findByIdWithComments(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return mapToPostResponse(post);
    }

    // --- Comment Operations (Many-to-One Relationship) ---

    @Transactional
    public CommentResponse addCommentToPost(CommentRequest request) {
        // 1. หา Post ที่ต้องการคอมเมนต์
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Cannot add comment, Post not found"));

        // 2. สร้าง Comment และเชื่อมความสัมพันธ์
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setPost(post); // กำหนด Foreign Key

        // 3. บันทึก Comment
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment.getId(), savedComment.getText());
    }

    // --- Helper Mappers (Manual Mapping) ---

    private PostResponse mapToPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());

        // แปลง List<Comment> (Entity) เป็น List<CommentResponse> (DTO)
        if (post.getComments() != null) {
            List<CommentResponse> commentDtos = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                commentDtos.add(new CommentResponse(comment.getId(), comment.getText()));
            }
            response.setComments(commentDtos);
        }

        return response;
    }
}
