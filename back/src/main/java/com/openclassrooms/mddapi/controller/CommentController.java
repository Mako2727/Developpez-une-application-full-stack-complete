package com.openclassrooms.mddapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.service.impl.CommentServiceImpl;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/{postId}")
    public ResponseEntity<String> addComment(@PathVariable Long postId,
                                             @RequestBody CommentCreateDTO commentDTO,
                                             Authentication authentication) {
        commentService.addComment(authentication, postId, commentDTO.getContent());
        return ResponseEntity.ok("{\"message\":\"Commentaire ajouté avec succès\"}");
    }
}
