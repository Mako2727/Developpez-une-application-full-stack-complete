package com.openclassrooms.mddapi.service.impl;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public String login(String email, String password) {
   
      Authentication auth =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(email, password));
      CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
      return jwtUtil.generateToken(userDetails.getUser());
   
  }

  public String register(RegisterDTO request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      //throw new IllegalArgumentException("Email déjà utilisé");
      String message="Email déjà utilisé";
      System.out.println("email..."+request.getEmail());
      return message;
    }

   
      User user = new User();
      user.setEmail(request.getEmail());
      user.setUsername(request.getUsername());
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      userRepository.save(user);

      return jwtUtil.generateToken(user);
    
  }

  public UserMeDTO  getCurrentUser(Authentication authentication) {
 CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();


    User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

         user = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<String> subscriptions = user.getSubscriptions()
            .stream()
            .map(subscription -> subscription.getTopic().getName())
            .collect(Collectors.toList());

        return new UserMeDTO(user.getEmail(), user.getUsername(), subscriptions);
  }

 public UserMeDTO updateCurrentUser(Authentication authentication, UserUpdateDTO updateDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

         user = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Vérifier que le nouvel email n'est pas déjà pris par un autre user
        if (!user.getEmail().equals(updateDTO.getEmail()) &&
            userRepository.findByEmail(updateDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // Modifier les champs
        user.setEmail(updateDTO.getEmail());
        user.setUsername(updateDTO.getUsername());

        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        userRepository.save(user);

        // Retourner le profil à jour
        List<String> subscriptions = user.getSubscriptions() == null ? List.of() :
            user.getSubscriptions().stream()
                .map(sub -> sub.getTopic().getName())
                .collect(Collectors.toList());

        return new UserMeDTO(user.getEmail(), user.getUsername(), subscriptions);
    }
}
