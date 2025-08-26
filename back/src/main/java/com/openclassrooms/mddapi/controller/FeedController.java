package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.service.impl.FeedServiceImpl;

@RestController
@RequestMapping("/api")
public class FeedController {

    private final FeedServiceImpl feedService;

    public FeedController(FeedServiceImpl feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDTO>> getUserFeed(Authentication authentication,@RequestParam(defaultValue = "desc") String sort) {
        List<PostDTO> feed = feedService.getUserFeed(authentication, sort);
        return ResponseEntity.ok(feed);
    }
}