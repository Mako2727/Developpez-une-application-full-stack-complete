package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.CustomUserDetailsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

class CustomUserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new CustomUserDetailsServiceImpl(userRepository);
    }

    @Test
    void testLoadUserByUsername_success() {
        String login = "testUser";

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        
        when(userRepository.findByUsernameOrEmail(login)).thenReturn(Optional.of(user));

       
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(login);

        
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_userNotFound() {
        String login = "unknownUser";

        when(userRepository.findByUsernameOrEmail(login)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(login));

        assertEquals("User not found with username or email: " + login, exception.getMessage());
    }
}