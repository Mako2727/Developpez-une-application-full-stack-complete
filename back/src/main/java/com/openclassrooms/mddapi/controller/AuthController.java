package com.openclassrooms.mddapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.model.AuthRequest;
import com.openclassrooms.mddapi.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthServiceImpl authService;

  public AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }

  @Operation(summary = "Recuperation du token")
  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
    String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
    return ResponseEntity.ok(Map.of("token", token));
  }

   @Operation(summary = "Creation d un utilisateur")
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterDTO request) {
    //affichage des parametres
      // Si ton DTO n’a pas de toString(), tu peux afficher champ par champ
    System.out.println("username = " + request.getUsername());
    System.out.println("email = " + request.getEmail());
    System.out.println("password = " + request.getPassword());

    String token = authService.register(request);
    return ResponseEntity.ok(Map.of("token", token));
  }

  @Operation(summary = "Recuperation des informations de l utilisateur")
  @GetMapping("/me")
  public ResponseEntity<UserMeDTO> getCurrentUser(Authentication authentication) {
    UserMeDTO dto = authService.getCurrentUser(authentication);
    return ResponseEntity.ok(dto);
}

    @PutMapping("/me")
    public ResponseEntity<UserMeDTO> updateProfile(Authentication authentication, @RequestBody UserUpdateDTO updateDTO) {
        UserMeDTO updatedUser = authService.updateCurrentUser(authentication, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
