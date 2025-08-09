package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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

@Service
public class FeedServiceImpl implements FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FeedServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getUserFeed(Authentication authentication, String sortOrder) {
        // Récupérer l'utilisateur connecté
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository
                .findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Récupérer les topics auxquels l'utilisateur est abonné
        List<Topic> subscribedTopics = user.getSubscriptions()
                .stream()
                .map(Subscription::getTopic)
                .collect(Collectors.toList());

        if (subscribedTopics.isEmpty()) {
            return List.of(); // Aucun abonnement → fil vide
        }


 List<Post> posts;
           if ("asc".equalsIgnoreCase(sortOrder)) {
         // Récupérer les posts liés à ces topics, triés du plus récent au plus ancien
       posts = postRepository.findByTopicInOrderByCreatedAtDesc(subscribedTopics);
    } else {
        // default desc
      posts = postRepository.findByTopicInOrderByCreatedAtAsc(subscribedTopics);
    }

      

        // Transformer en DTO (éviter de renvoyer l'entité brute)
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
