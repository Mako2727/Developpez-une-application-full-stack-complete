package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.FeedServiceImpl;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

class FeedServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private FeedServiceImpl feedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Injection manuelle du JwtUtil mocké dans le champ private
        ReflectionTestUtils.setField(feedService, "jwtUtil", jwtUtil);
    }

    @Test
    void testGetUserFeed_noSubscriptions() {
        User user = new User();
        user.setSubscriptions(List.of()); // pas d'abonnement

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);

        List<PostDTO> feed = feedService.getUserFeed(authentication, "asc");

        assertNotNull(feed);
        assertTrue(feed.isEmpty());
    }

   @Test
void testGetUserFeed_ascOrder() {
    // Préparer l'utilisateur et ses abonnements
    User user = new User();
    Subscription subscription = new Subscription();
    Topic topic = new Topic();
    topic.setName("Tech");
    subscription.setTopic(topic);
    user.setSubscriptions(List.of(subscription));

    Post post = new Post();
    post.setId(1L);
    post.setTitle("Post 1");
    post.setContent("Contenu");
    post.setTopic(topic);

    // Mock JwtUtil pour retourner l'utilisateur connecté
    when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(user);

    // Mock PostRepository pour la méthode réellement appelée pour "asc"
    when(postRepository.findByTopicInOrderByCreatedAtDesc(List.of(topic)))
            .thenReturn(List.of(post));

    // Appel de la méthode à tester
    List<PostDTO> feed = feedService.getUserFeed(authentication, "asc");

    // Assertions
    assertNotNull(feed);
    assertEquals(1, feed.size());
    assertEquals("Post 1", feed.get(0).getTitle());
}
}