package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.FeedService;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

@Service
public class FeedServiceImpl implements FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
     @Autowired private JwtUtil jwtUtil;

    public FeedServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getUserFeed(Authentication authentication, String sortOrder) {
         User authUser=   jwtUtil.getUserFromAuthent(authentication);  

       
        List<Topic> subscribedTopics = authUser.getSubscriptions()
                .stream()
                .map(Subscription::getTopic)
                .collect(Collectors.toList());

        if (subscribedTopics.isEmpty()) {
            return List.of(); 
        }


 List<Post> posts;
           if ("asc".equalsIgnoreCase(sortOrder)) {
         
       posts = postRepository.findByTopicInOrderByCreatedAtDesc(subscribedTopics);
    } else {
        
      posts = postRepository.findByTopicInOrderByCreatedAtAsc(subscribedTopics);
    }

      

       
        return posts.stream()
                .map(post -> new PostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getTopic().getName()
                ))
                .collect(Collectors.toList());
    }
}
