package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.impl.AuthServiceImpl;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        Topic topic = new Topic();
        topic.setName("Spring");
        Subscription sub = new Subscription();
        sub.setTopic(topic);
        user.setSubscriptions(List.of(sub));
    }

    @Test
    void login_shouldReturnToken() {
        Authentication authMock = mock(Authentication.class);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        when(authMock.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);
        when(jwtUtil.generateToken(user)).thenReturn("mock-token");

        String token = authService.login("test@test.com", "password");

        assertEquals("mock-token", token);
    }

    @Test
    void register_shouldReturnErrorIfEmailExists() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("test@test.com");
        dto.setUsername("newUser");
        dto.setPassword("pass");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        String result = authService.register(dto);

        assertEquals("Email déjà utilisé", result);
    }

    @Test
    void register_shouldSaveUserAndReturnToken() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("new@test.com");
        dto.setUsername("newUser");
        dto.setPassword("pass");

        when(userRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        when(jwtUtil.generateToken(any(User.class))).thenReturn("mock-token");

        String token = authService.register(dto);

        assertEquals("mock-token", token);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("new@test.com", captor.getValue().getEmail());
        assertEquals("encodedPass", captor.getValue().getPassword());
    }

    @Test
    void getCurrentUser_shouldReturnUserMeDTO() {
        Authentication auth = mock(Authentication.class);
        when(jwtUtil.getUserFromAuthent(auth)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserMeDTO dto = authService.getCurrentUser(auth);

        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(List.of("Spring"), dto.getSubscriptions());
    }

    @Test
    void updateCurrentUser_shouldUpdateAndReturnDTO() {
        Authentication auth = mock(Authentication.class);
        when(jwtUtil.getUserFromAuthent(auth)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("updated@test.com");
        updateDTO.setUsername("updatedUser");
        updateDTO.setPassword("newPass");

        UserMeDTO dto = authService.updateCurrentUser(auth, updateDTO);

        assertEquals("updated@test.com", dto.getEmail());
        assertEquals("updatedUser", dto.getUsername());
        assertEquals(List.of("Spring"), dto.getSubscriptions());
        verify(userRepository).save(user);
    }

    @Test
    void updateCurrentUser_shouldThrowIfEmailExists() {
        Authentication auth = mock(Authentication.class);
        User otherUser = new User();
        otherUser.setEmail("taken@test.com");

        when(jwtUtil.getUserFromAuthent(auth)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("taken@test.com")).thenReturn(Optional.of(otherUser));

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("taken@test.com");
        updateDTO.setUsername("updatedUser");

        assertThrows(IllegalArgumentException.class,
                     () -> authService.updateCurrentUser(auth, updateDTO));
    }
}