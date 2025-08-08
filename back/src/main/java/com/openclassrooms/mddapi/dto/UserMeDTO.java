package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMeDTO {
    private Long id;
    private String email;
    private String name;
    private List<String> subscriptions; 

     public UserMeDTO(String email, String username, List<String> subscriptions) {
        this.email = email;
        this.name = username;
        this.subscriptions = subscriptions;
    }

    // Getters et setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return name; }
    public void setUsername(String username) { this.name = username; }

    public List<String> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(List<String> subscriptions) { this.subscriptions = subscriptions; }

}