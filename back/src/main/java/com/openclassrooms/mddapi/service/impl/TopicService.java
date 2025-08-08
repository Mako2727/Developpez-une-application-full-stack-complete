package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;


@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
   private final SubscriptionRepository subscriptionRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<TopicWithSubscriptionDTO> getAllTopicsWithSubscriptionStatus(Authentication authentication) {
         CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

         user = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<Topic> allTopics = topicRepository.findAll();

        Set<Long> subscribedTopicIds = user.getSubscriptions()
            .stream()
            .map(subscription -> subscription.getTopic().getId())
            .collect(Collectors.toSet());

        return allTopics.stream()
            .map(topic -> new TopicWithSubscriptionDTO(
                topic.getId(),
                topic.getName(),
                subscribedTopicIds.contains(topic.getId())
            ))
            .collect(Collectors.toList());
    }

 public void subscribeUserToTopic(Authentication authentication, Long topicId) {
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
System.out.println("Email extrait de l'authentication : " + user.getEmail());
         user = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        boolean alreadySubscribed = subscriptionRepository.existsByUserAndTopic(user, topic);

        if (alreadySubscribed) {
            throw new IllegalStateException("Vous êtes déjà abonné à ce thème");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        subscriptionRepository.save(subscription);
    }

public void unsubscribeUserFromTopic(Authentication authentication, Long topicId) {
   CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

     user = userRepository.findByEmail(user.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    Topic topic = topicRepository.findById(topicId)
        .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

    Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic)
        .orElseThrow(() -> new IllegalStateException("Abonnement non trouvé"));

    subscriptionRepository.delete(subscription);
}
}
