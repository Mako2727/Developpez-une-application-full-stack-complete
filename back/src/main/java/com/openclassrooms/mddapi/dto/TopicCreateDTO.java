package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class TopicCreateDTO {

    @NotBlank(message = "Le nom du thème est obligatoire")
    private String name;

    private String description;
}
