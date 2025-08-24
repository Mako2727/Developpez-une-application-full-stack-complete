package com.openclassrooms.mddapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.CommentServiceImpl;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.util.Optional;

class CommentServiceImplTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Authentication authentication;

    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Injection via constructeur
        commentService = new CommentServiceImpl(commentRepository, userRepository, postRepository);

        // Injection manuelle du mock JwtUtil dans le champ @Autowired
        Field jwtUtilField = CommentServiceImpl.class.getDeclaredField("jwtUtil");
        jwtUtilField.setAccessible(true);
        jwtUtilField.set(commentService, jwtUtil);
    }

    @Test
    void testAddComment_success() {
        Long postId = 1L;
        String content = "Super commentaire !";

        User user = new User();
        user.setEmail("user@test.com");

        Post post = new Post();
        post.setId(postId);

        // Mock JwtUtil pour retourner l'utilisateur connecté
        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);

        // Mock UserRepository
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Mock PostRepository
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Appel de la méthode
        commentService.addComment(authentication, postId, content);

        // Vérifie que le commentaire a été sauvegardé
        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());

        Comment savedComment = captor.getValue();
        assertEquals(user, savedComment.getAuthor());
        assertEquals(post, savedComment.getPost());
        assertEquals(content, savedComment.getContent());
    }

    @Test
    void testAddComment_postNotFound() {
        Long postId = 1L;
        String content = "Test";

        User user = new User();
        user.setEmail("user@test.com");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commentService.addComment(authentication, postId, content));
        assertEquals("Article non trouvé", exception.getMessage());
    }

    @Test
    void testAddComment_userNotFound() {
        Long postId = 1L;
        String content = "Test";

        User user = new User();
        user.setEmail("user@test.com");

        Post post = new Post();
        post.setId(postId);

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> commentService.addComment(authentication, postId, content));
    }
}