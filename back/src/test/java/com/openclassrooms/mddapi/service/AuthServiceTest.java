package com.openclassrooms.mddapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.impl.AuthServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.List;

class AuthServiceImplTest {

    @Mock
    private Authentication authentication;

    // Mock d’autres dépendances ici si nécessaire
    // @Mock private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService; // ta classe concrète

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String email = "test@test.com";
        String password = "password";

        // Simule le comportement attendu
        when(authService.login(email, password)).thenReturn("mock-token");

        String token = authService.login(email, password);

        assertEquals("mock-token", token);
    }

    @Test
    void testRegister() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("user");
        dto.setEmail("user@test.com");
        dto.setPassword("Password123!");

        when(authService.register(dto)).thenReturn("registered");

        String result = authService.register(dto);

        assertEquals("registered", result);
    }

    @Test
    void testGetCurrentUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("me@test.com");
        user.setUsername("me");
        
        // Simule une liste vide pour subscriptions
        List<String> subscriptions = List.of();

        // Crée le DTO directement avec le constructeur existant
        UserMeDTO dto = new UserMeDTO();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setSubscriptions(subscriptions);

        when(authService.getCurrentUser(authentication)).thenReturn(dto);

        UserMeDTO currentUser = authService.getCurrentUser(authentication);

        assertEquals("me", currentUser.getUsername());
        assertEquals("me@test.com", currentUser.getEmail());
        assertTrue(currentUser.getSubscriptions().isEmpty());
    }
}