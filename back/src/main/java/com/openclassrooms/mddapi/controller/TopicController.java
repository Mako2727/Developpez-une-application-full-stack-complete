package com.openclassrooms.mddapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.MessageResponseDTO;
import com.openclassrooms.mddapi.dto.SubscribedTopicDTO;
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

@PostMapping("/create")
public ResponseEntity<MessageResponseDTO> createTopic(@RequestBody String rawBody,Authentication authentication,@Valid  @RequestBody TopicCreateDTO dto) {
     String message= topicService.createTopic(authentication,dto);
    return ResponseEntity.ok(new MessageResponseDTO(message));
}

@PostMapping("{topicId}/subscribe")
public ResponseEntity<MessageResponseDTO> subscribeToTopic(@PathVariable Long topicId, Authentication authentication) {
   String message= topicService.subscribeUserToTopic(authentication, topicId);
    return ResponseEntity.ok(new MessageResponseDTO(message));
}

@DeleteMapping("{topicId}")
public ResponseEntity<MessageResponseDTO> unsubscribeFromTopic(@PathVariable Long topicId, Authentication authentication) {
   String message= topicService.unsubscribeUserFromTopic(authentication, topicId);
   return ResponseEntity.ok(new MessageResponseDTO(message));
}

@GetMapping("/subscribed")
public ResponseEntity<List<SubscribedTopicDTO>> getSubscribedTopics() {
    List<SubscribedTopicDTO> subscribedTopics = topicService.getSubscribedTopicsForCurrentUser();
    return ResponseEntity.ok(subscribedTopics);
}

}
