package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.username", target = "authorName") // User a une propriété username
    @Mapping(source = "topic.name", target = "topicName")       // Topic a une propriété name
    PostDetailDTO toDetailDTO(Post post);

    List<PostDetailDTO> toDetailDTOList(List<Post> posts);
}