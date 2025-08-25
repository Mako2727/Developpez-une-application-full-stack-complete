package com.openclassrooms.mddapi.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.AuthService;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceImplIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        
        User testUser = new User();
        testUser.setEmail("user@test.com");
        testUser.setUsername("TestUser");
        testUser.setPassword("password123"); 
        testUser.setSubscriptions(List.of()); 
        userRepository.save(testUser);
    }

    @Test
    public void testGetCurrentUser_shouldReturnUserMeDTO() {
        User user = userRepository.findByEmail("user@test.com").orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(user); 
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        UserMeDTO dto = authService.getCurrentUser(authentication);

        assertNotNull(dto);
        assertEquals("user@test.com", dto.getEmail());
        assertEquals("TestUser", dto.getUsername());
        assertTrue(dto.getSubscriptions().isEmpty());
    }

    @Test
    public void testRegister_shouldCreateUserAndReturnJwt() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("newuser@test.com");
        dto.setUsername("NewUser");
        dto.setPassword("newpassword");

        String token = authService.register(dto);

        assertNotNull(token);

        User created = userRepository.findByEmail("newuser@test.com").orElse(null);
        assertNotNull(created);
        assertEquals("NewUser", created.getUsername());
    }

    @Test
    public void testRegister_existingEmail_shouldReturnMessage() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("user@test.com"); 
        dto.setUsername("AnotherUser");
        dto.setPassword("pass");

        String result = authService.register(dto);

        assertEquals("Email déjà utilisé", result);
    }
}