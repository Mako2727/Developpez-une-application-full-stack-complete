package com.openclassrooms.mddapi.service;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;

public interface PostService {
     public void createPost(Authentication authentication, PostCreateDTO dto) ;
     public PostDetailDTO getPostDetail(Long postId) ;
    
}
