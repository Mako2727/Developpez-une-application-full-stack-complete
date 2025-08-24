package com.openclassrooms.mddapi.springSecurity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private jwtRequestFilter jwtRequestFilter;

    @Test
    void contextLoads() {
        // Vérifie que tous les beans principaux sont correctement créés
        assertThat(securityFilterChain).isNotNull();
        assertThat(authenticationManager).isNotNull();
        assertThat(passwordEncoder).isNotNull();
        assertThat(userDetailsService).isNotNull();
        assertThat(jwtRequestFilter).isNotNull();
    }
}