package com.example.newsportal.service.mapper;

import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post convertPostDto(PostDto postDto, User user) {
        return new Post(postDto.getHeader(), postDto.getDescription(), postDto.getPhotoOrVideoUrl(), user);
    }
}
