package com.openclassrooms.mddapi.springSecurity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtRequestFilterTest {

    @InjectMocks
    private jwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter.setUserDetailsService(userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_noAuthorizationHeader_shouldCallFilterChainOnly() throws Exception {
        when(request.getServletPath()).thenReturn("/api/other");
        when(request.getHeader("authorization")).thenReturn(null);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_withValidJwt_shouldAuthenticateUser() throws Exception {
        when(request.getServletPath()).thenReturn("/api/other");
        when(request.getHeader("authorization")).thenReturn("Bearer mockToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("authorization")));
        when(jwtUtil.extractUsername("mockToken")).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_loginPath_shouldSkipAuthentication() throws Exception {
        when(request.getServletPath()).thenReturn("/api/auth/login");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}