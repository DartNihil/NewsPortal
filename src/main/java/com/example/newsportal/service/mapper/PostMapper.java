package com.example.newsportal.service.mapper;

import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post convertPostDto(PostDto postDto){
        return new Post(postDto.getHeader(), postDto.getDescription(), postDto.getPhotoOrVideoUrl());
    }
}
