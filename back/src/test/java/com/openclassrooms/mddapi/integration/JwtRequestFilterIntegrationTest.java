package com.openclassrooms.mddapi.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;
import com.openclassrooms.mddapi.springSecurity.jwtRequestFilter;

@SpringBootTest
@ActiveProfiles("test")
public class JwtRequestFilterIntegrationTest {

    // Sous-classe test-only pour exposer doFilterInternal
    private static class TestJwtRequestFilter extends jwtRequestFilter {
        public TestJwtRequestFilter(JwtUtil jwtUtil) {
            super(jwtUtil); // appel explicite au constructeur existant
        }

        public void doFilterPublic(MockHttpServletRequest request,
                                   MockHttpServletResponse response,
                                   MockFilterChain filterChain) throws Exception {
            super.doFilterInternal(request, response, filterChain);
        }
    }

    private TestJwtRequestFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private String token;

    @BeforeEach
    public void setup() {
        // Création du filter test en passant JwtUtil
        jwtFilter = new TestJwtRequestFilter(jwtUtil);

        // Injection d'un UserDetailsService minimal pour le test
        jwtFilter.setUserDetailsService(username -> {
            User u = userRepository.findByEmail(username).orElseThrow();
            return new CustomUserDetails(u);
        });

        // Création d'un utilisateur test
        user = new User();
        user.setEmail("user@test.com");
        user.setUsername("TestUser");
        user.setPassword("password123");
        userRepository.save(user);

        // Génération d'un token JWT pour l'utilisateur
        token = jwtUtil.generateToken(user);
    }

    @Test
    public void testFilter_setsAuthentication_whenValidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        request.addHeader("Authorization", "Bearer " + token);

        // Exécuter le filter via la méthode publique test-only
        jwtFilter.doFilterPublic(request, response, filterChain);

        // Vérifier que le SecurityContext contient bien l'authentification
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertTrue(auth.getPrincipal() instanceof CustomUserDetails);

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        assertEquals("user@test.com", userDetails.getUser().getEmail());
    }
}