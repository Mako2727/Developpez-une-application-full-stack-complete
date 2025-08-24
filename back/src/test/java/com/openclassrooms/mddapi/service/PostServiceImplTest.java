package com.openclassrooms.mddapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.*;
import com.openclassrooms.mddapi.repository.*;
import com.openclassrooms.mddapi.service.impl.PostServiceImpl;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PostServiceImpl postService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    postService = new PostServiceImpl(postRepository, topicRepository, userRepository, postMapper);
    // Injection manuelle du JwtUtil mocké
    java.lang.reflect.Field jwtField;
    try {
        jwtField = PostServiceImpl.class.getDeclaredField("jwtUtil");
        jwtField.setAccessible(true);
        jwtField.set(postService, jwtUtil);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    @Test
    void testCreatePost_success() {
        PostCreateDTO dto = new PostCreateDTO();
        dto.setTitle("Titre");
        dto.setContent("Contenu");
        dto.setTopicId(1L);

        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("userTest");

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Thème 1");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(topicRepository.findById(dto.getTopicId())).thenReturn(Optional.of(topic));

        assertDoesNotThrow(() -> postService.createPost(authentication, dto));

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testCreatePost_userNotFound() {
        PostCreateDTO dto = new PostCreateDTO();
        dto.setTitle("Titre");
        dto.setContent("Contenu");
        dto.setTopicId(1L);

        User user = new User();
        user.setEmail("test@example.com");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> postService.createPost(authentication, dto));
    }

    @Test
    void testCreatePost_topicNotFound() {
        PostCreateDTO dto = new PostCreateDTO();
        dto.setTitle("Titre");
        dto.setContent("Contenu");
        dto.setTopicId(1L);

        User user = new User();
        user.setEmail("test@example.com");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(topicRepository.findById(dto.getTopicId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> postService.createPost(authentication, dto));
    }

    @Test
    void testGetPostDetail_success() {
        User author = new User();
        author.setUsername("userTest");

        Topic topic = new Topic();
        topic.setName("Thème 1");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Titre");
        post.setContent("Contenu");
        post.setAuthor(author);
        post.setTopic(topic);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setComments(Collections.emptyList()); // éviter NPE

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDetailDTO result = postService.getPostDetail(1L);

        assertNotNull(result);
        assertEquals("Titre", result.getTitle());
        assertEquals("userTest", result.getAuthorName());
        assertEquals("Thème 1", result.getTopicName());
    }

    @Test
    void testGetPostDetail_notFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> postService.getPostDetail(1L));
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toDetailDTOList(posts)).thenReturn(Collections.singletonList(new PostDetailDTO()));

        List<PostDetailDTO> result = postService.getAllPosts();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}