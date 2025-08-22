package com.openclassrooms.mddapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.mddapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  @Query("SELECT u FROM User u WHERE u.username = :login OR u.email = :login")
    Optional<User> findByUsernameOrEmail(@Param("login") String login);
}
