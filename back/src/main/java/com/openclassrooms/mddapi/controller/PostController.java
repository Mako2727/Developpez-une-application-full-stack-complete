package com.openclassrooms.mddapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.MessageResponseDTO;
import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.impl.PostServiceImpl;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createPost(@RequestBody PostCreateDTO postCreateDTO, Authentication authentication) {
        postService.createPost(authentication, postCreateDTO);
        return ResponseEntity.ok(new MessageResponseDTO("Post créé"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPost(@PathVariable Long id) {
        PostDetailDTO postDetail = postService.getPostDetail(id);
        return ResponseEntity.ok(postDetail);
    }

}
