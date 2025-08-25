package com.openclassrooms.mddapi.springSecurity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setUsername("testuser");
        testUser.setPassword("password");
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtil.generateToken(testUser);
        assertNotNull(token);

        boolean valid = jwtUtil.validateToken(token, testUser);
        assertTrue(valid);

        String email = jwtUtil.extractEmail(token);
        assertEquals(testUser.getEmail(), email);

        String username = jwtUtil.extractUsername(token);
        assertEquals(testUser.getEmail(), username); 
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        String token = jwtUtil.generateToken(testUser);
        assertFalse(jwtUtil.extractExpiration(token).before(new Date(System.currentTimeMillis())));
    }

    @Test
    void testGetUserFromAuthent_success() {
        CustomUserDetails customUserDetails = new CustomUserDetails(testUser);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        User userFromAuth = jwtUtil.getUserFromAuthent(authentication);
        assertEquals(testUser, userFromAuth);
    }

    @Test
    void testGetUserFromAuthent_userNotFound() {
        CustomUserDetails customUserDetails = new CustomUserDetails(testUser);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> jwtUtil.getUserFromAuthent(authentication));
    }
}
