package com.openclassrooms.mddapi.service;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.model.User;
public interface AuthService {
  public String login(String email, String password) ;

  public String register(RegisterDTO request) ;

  public UserMeDTO  getCurrentUser(Authentication authentication) ;


}


