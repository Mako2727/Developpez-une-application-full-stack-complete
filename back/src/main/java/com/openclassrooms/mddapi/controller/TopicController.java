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

import com.openclassrooms.mddapi.dto.MessageResponseDTO;
import com.openclassrooms.mddapi.dto.TopicCreateDTO;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.dto.UserMeDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.impl.TopicServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicServiceImpl topicService;

    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicWithSubscriptionDTO>> getAllTopics(Authentication authentication) {
         List<TopicWithSubscriptionDTO> topics = topicService.getAllTopicsWithSubscriptionStatus(authentication);
        return ResponseEntity.ok(topics);
    }

@PostMapping("{topicId}")
public ResponseEntity<MessageResponseDTO> subscribeToTopic(@PathVariable Long topicId, Authentication authentication) {
    System.out.println("Get Name : " + authentication.getName());
   String message= topicService.subscribeUserToTopic(authentication, topicId);
    return ResponseEntity.ok(new MessageResponseDTO(message));
}

@DeleteMapping("{topicId}")
public ResponseEntity<String> unsubscribeFromTopic(@PathVariable Long topicId, Authentication authentication) {
    topicService.unsubscribeUserFromTopic(authentication, topicId);
    return ResponseEntity.ok("Désabonnement effectué avec succès");
}

@PostMapping("/create")
public ResponseEntity<Topic> createTopic(@Valid  @RequestBody TopicCreateDTO dto,Authentication authentication) {
    System.out.println("Nom reçu DTO : " + dto.getName());
    System.out.println("Description reçue DTO : " + dto.getDescription());
     System.out.println("Nom reçu DTO description : " + dto.getDescription());
    Topic topic = topicService.createTopic(authentication,dto);
    return ResponseEntity.ok(topic);
}

}
