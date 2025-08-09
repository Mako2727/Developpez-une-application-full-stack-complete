package com.openclassrooms.mddapi.service;

import org.springframework.security.core.Authentication;

public interface CommentService {

     public void addComment(Authentication authentication, Long postId, String content) ;
    
}
