package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.openclassrooms.mddapi.dto.TopicCreateDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.TopicServiceImpl;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

class TopicServiceImplTest {

    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setUsername("TestUser");
    }

@Test
void testCreateTopic_success() {
    TopicCreateDTO dto = new TopicCreateDTO();
    dto.setName("Nouveau Topic");
    dto.setDescription("Description Topic");

    
    CustomUserDetails customUserDetails = new CustomUserDetails(testUser);

    when(authentication.getPrincipal()).thenReturn(customUserDetails);
    when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

    String message = topicService.createTopic(authentication, dto);

    assertEquals("Le Topic vient d etre créé", message); 
    verify(topicRepository, times(1)).save(any(Topic.class));
}

    @Test
    void testSubscribeUserToTopic_success() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Topic Test");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(testUser);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(subscriptionRepository.existsByUserAndTopic(testUser, topic)).thenReturn(false);

        String message = topicService.subscribeUserToTopic(authentication, 1L);

        assertEquals("Vous êtes désormé abonné à ce thème", message);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testUnsubscribeUserFromTopic_success() {
        Topic topic = new Topic();
        topic.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setUser(testUser);
        subscription.setTopic(topic);

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(testUser);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(subscriptionRepository.findByUserAndTopic(testUser, topic)).thenReturn(Optional.of(subscription));

        String message = topicService.unsubscribeUserFromTopic(authentication, 1L);

        assertEquals("Vous êtes désormé desabonné de ce thème", message);
        verify(subscriptionRepository, times(1)).delete(subscription);
    }

    @Test
    void testGetAllTopicsWithSubscriptionStatus() {
        Topic topic1 = new Topic();
        topic1.setId(1L);
        topic1.setName("Topic1");

        when(jwtUtil.getUserFromAuthent(authentication)).thenReturn(testUser);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(topicRepository.findAll()).thenReturn(List.of(topic1));
        testUser.setSubscriptions(List.of()); 

        var topics = topicService.getAllTopicsWithSubscriptionStatus(authentication);

        assertEquals(1, topics.size());
        assertEquals("Topic1", topics.get(0).getName());
    }
}