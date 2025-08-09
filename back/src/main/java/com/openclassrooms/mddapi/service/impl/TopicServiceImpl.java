package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.TopicCreateDTO;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;


@Service
public class TopicServiceImpl  implements TopicService{

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
   private final SubscriptionRepository subscriptionRepository;
  @Autowired private JwtUtil jwtUtil;
    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<TopicWithSubscriptionDTO> getAllTopicsWithSubscriptionStatus(Authentication authentication) {
         User authUser=   jwtUtil.getUserFromAuthent(authentication);  

       User  user = userRepository.findByEmail(authUser.getEmail())
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

 public String subscribeUserToTopic(Authentication authentication, Long topicId) {
      String message;
       User authUser=   jwtUtil.getUserFromAuthent(authentication);  

       User  user = userRepository.findByEmail(authUser.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        boolean alreadySubscribed = subscriptionRepository.existsByUserAndTopic(user, topic);

        if (alreadySubscribed) {
            //throw new IllegalStateException("Vous êtes déjà abonné à ce thème");
            message="Vous êtes déjà abonné à ce thème";
            return message;
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        subscriptionRepository.save(subscription);
        message="Vous êtes désormé abonné à ce thème";
         return message;
    }

public void unsubscribeUserFromTopic(Authentication authentication, Long topicId) {
   User authUser=   jwtUtil.getUserFromAuthent(authentication);  

       User  user = userRepository.findByEmail(authUser.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    Topic topic = topicRepository.findById(topicId)
        .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

    Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic)
        .orElseThrow(() -> new IllegalStateException("Abonnement non trouvé"));

    subscriptionRepository.delete(subscription);
}

public Topic createTopic(Authentication authentication, TopicCreateDTO dto) {
 CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    User creator = userRepository.findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    System.out.println("Nom reçu getNameXXX : " + dto.getDescription());
    System.out.println("Nom reçu creatorXXX : " + creator.getId());
      Topic topic = new Topic();
        topic.setName(dto.getName());
        topic.setDescription(dto.getDescription());
        topic.setCreator(creator); 
        return topicRepository.save(topic);
}
}
