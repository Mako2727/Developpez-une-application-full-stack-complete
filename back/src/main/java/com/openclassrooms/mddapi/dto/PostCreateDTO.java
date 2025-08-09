package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {
    private Long topicId;
    private String title;
    private String content;
}
