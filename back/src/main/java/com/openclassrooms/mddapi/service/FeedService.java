package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.PostDTO;

public interface FeedService {
    public List<PostDTO> getUserFeed(Authentication authentication, String sortOrder);
}
