package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.username", target = "authorName") 
    @Mapping(source = "topic.name", target = "topicName")       
    PostDetailDTO toDetailDTO(Post post);

    List<PostDetailDTO> toDetailDTOList(List<Post> posts);
}