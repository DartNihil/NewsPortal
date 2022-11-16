package com.example.newsportal.service;

import com.example.newsportal.entity.Post;
import com.example.newsportal.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(Post post) {
        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
    }
}
