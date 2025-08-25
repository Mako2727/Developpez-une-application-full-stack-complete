package com.openclassrooms.mddapi.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.SubscribedTopicDTO;
import com.openclassrooms.mddapi.dto.TopicCreateDTO;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.springSecurity.JwtUtil;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;


@Service
public class TopicServiceImpl  implements TopicService{

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
   private final SubscriptionRepository subscriptionRepository;
    private final UserService userService; 
     
  @Autowired private JwtUtil jwtUtil;
     public TopicServiceImpl(TopicRepository topicRepository,
                            UserRepository userRepository,
                            SubscriptionRepository subscriptionRepository,
                            UserService userService,
                            JwtUtil jwtUtil) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
                subscribedTopicIds.contains(topic.getId()),
                topic.getDescription()
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

public String unsubscribeUserFromTopic(Authentication authentication, Long topicId) {
      String message;
   User authUser=   jwtUtil.getUserFromAuthent(authentication);  

       User  user = userRepository.findByEmail(authUser.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    Topic topic = topicRepository.findById(topicId)
        .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

    Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic)
        .orElseThrow(() -> new IllegalStateException("Abonnement non trouvé"));

    subscriptionRepository.delete(subscription);
      message="Vous êtes désormé desabonné de ce thème";
         return message;
}

public String createTopic(Authentication authentication, TopicCreateDTO dto) {
    String message;
 CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    User creator = userRepository.findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
  
      Topic topic = new Topic();
        topic.setName(dto.getName());
        topic.setDescription(dto.getDescription());
        topic.setCreator(creator); 
         topicRepository.save(topic);
          message="Le Topic vient d etre créé";
         return message;
}

@Override
public List<SubscribedTopicDTO> getSubscribedTopicsForCurrentUser() {
    User currentUser = userService.getCurrentUser();
    List<Subscription> subscriptions = subscriptionRepository.findByUser(currentUser);

    return subscriptions.stream()
        .map(sub -> {
            Topic t = sub.getTopic();
            return new SubscribedTopicDTO(
                t.getId(),
                t.getName(),
                t.getDescription(),
                new SubscribedTopicDTO.CreatorDTO(
                    t.getCreator().getId(),
                    t.getCreator().getUsername()
                )
            );
        })
        .toList();
}

}
