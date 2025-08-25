package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepository);

        
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetCurrentUser_success() {
       
        when(authentication.getName()).thenReturn(testUser.getUsername());
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(testUser.getUsername(), currentUser.getUsername());
        assertEquals(testUser.getEmail(), currentUser.getEmail());
    }

    @Test
    void testGetCurrentUser_userNotFound() {
        when(authentication.getName()).thenReturn("unknownuser");
        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

       Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
    userService.getCurrentUser();
        });

        assertTrue(exception.getMessage().contains("Utilisateur non trouvé"));
    }
}
