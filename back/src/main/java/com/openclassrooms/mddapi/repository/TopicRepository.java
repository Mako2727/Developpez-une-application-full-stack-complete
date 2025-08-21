package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.mddapi.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
      @Query("SELECT t FROM Topic t JOIN t.subscriptions s WHERE s.user.id = :userId")
    List<Topic> findSubscribedTopicsByUserId(@Param("userId") Long userId);
}
