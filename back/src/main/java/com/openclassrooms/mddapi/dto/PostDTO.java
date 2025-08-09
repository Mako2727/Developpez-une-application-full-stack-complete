package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // génère getters/setters, equals, hashCode, toString
@NoArgsConstructor // constructeur vide
@AllArgsConstructor // constructeur complet
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String topicName;
}
