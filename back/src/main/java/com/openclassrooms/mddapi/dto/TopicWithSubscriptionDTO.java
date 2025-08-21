package com.openclassrooms.mddapi.dto;

public class TopicWithSubscriptionDTO {
    private Long id;
    private String name;
    private boolean subscribed;
    private String description; 

    public TopicWithSubscriptionDTO(Long id, String name, boolean subscribed, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subscribed = subscribed;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isSubscribed() { return subscribed; }
    public void setSubscribed(boolean subscribed) { this.subscribed = subscribed; }

      public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
