package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
     private final com.openclassrooms.mddapi.mapper.PostMapper postMapper; 
 @Autowired private JwtUtil jwtUtil;
    public PostServiceImpl(PostRepository postRepository, TopicRepository topicRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    public void createPost(Authentication authentication, PostCreateDTO dto) {

   User authUser=   jwtUtil.getUserFromAuthent(authentication);  

        User author = userRepository.findByEmail(authUser.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(dto.getTopicId())
            .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        Post post = new Post();
        post.setAuthor(author);
        post.setTopic(topic);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        postRepository.save(post);
    }

      public PostDetailDTO getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));

        List<CommentDTO> commentDTOs = post.getComments().stream()
            .map(c -> new CommentDTO(
                c.getId(),
                c.getContent(),
                c.getAuthor().getUsername(),
                c.getCreatedAt()))
            .collect(Collectors.toList());

        return new PostDetailDTO(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getTopic().getName(),
            post.getAuthor().getUsername(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            commentDTOs
        );
    }


  @Override
public List<PostDetailDTO> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return postMapper.toDetailDTOList(posts);
}
}
