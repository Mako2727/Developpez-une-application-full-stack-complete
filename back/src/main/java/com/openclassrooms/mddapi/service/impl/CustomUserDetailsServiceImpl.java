package com.openclassrooms.mddapi.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.model.CustomUserDetails;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService  {

    private final UserRepository userRepository;

  public CustomUserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

 @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + login
                ));
        return new CustomUserDetails(user);
    }

  
    
}
