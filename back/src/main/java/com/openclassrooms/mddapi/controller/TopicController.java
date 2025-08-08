package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.service.impl.TopicService;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicWithSubscriptionDTO>> getAllTopics(Authentication authentication) {
         List<TopicWithSubscriptionDTO> topics = topicService.getAllTopicsWithSubscriptionStatus(authentication);
        return ResponseEntity.ok(topics);
    }

@PostMapping("{topicId}")
public ResponseEntity<String> subscribeToTopic(@PathVariable Long topicId, Authentication authentication) {
    System.out.println("Get Name : " + authentication.getName());
    topicService.subscribeUserToTopic(authentication, topicId);
    return ResponseEntity.ok("Abonnement effectué avec succès");
}

@DeleteMapping("{topicId}")
public ResponseEntity<String> unsubscribeFromTopic(@PathVariable Long topicId, Authentication authentication) {
    topicService.unsubscribeUserFromTopic(authentication, topicId);
    return ResponseEntity.ok("Désabonnement effectué avec succès");
}

}
