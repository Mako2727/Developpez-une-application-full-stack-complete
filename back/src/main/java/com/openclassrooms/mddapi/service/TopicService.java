package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.SubscribedTopicDTO;
import com.openclassrooms.mddapi.dto.TopicCreateDTO;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionDTO;
import com.openclassrooms.mddapi.model.Topic;

public interface TopicService {
    public List<TopicWithSubscriptionDTO> getAllTopicsWithSubscriptionStatus(Authentication authentication);
     public String subscribeUserToTopic(Authentication authentication, Long topicId) ;
    public String unsubscribeUserFromTopic(Authentication authentication, Long topicId) ;
public String createTopic(Authentication authentication, TopicCreateDTO dto);



public List<SubscribedTopicDTO> getSubscribedTopicsForCurrentUser() ;


}



