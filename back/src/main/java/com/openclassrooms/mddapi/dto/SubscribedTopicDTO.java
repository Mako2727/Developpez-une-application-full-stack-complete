package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
public class SubscribedTopicDTO {

    private Long id;
    private String name;
    private String description;
    private CreatorDTO creator;

    public SubscribedTopicDTO() {}

    public SubscribedTopicDTO(Long id, String name, String description, CreatorDTO creator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public static class CreatorDTO {
        private Long id;
        private String username;

        public CreatorDTO() {}
        public CreatorDTO(Long id, String username) {
            this.id = id;
            this.username = username;
        }

        public Long getId() { return id; }
        public String getUsername() { return username; }

        public void setId(Long id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
    }

    // getters / setters pour SubscribedTopicDTO
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public CreatorDTO getCreator() { return creator; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCreator(CreatorDTO creator) { this.creator = creator; }
}