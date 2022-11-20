package com.example.newsportal.service;

import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.repository.PostRepository;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public Post save(Post post) {
        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    public Post mapPostDto(PostDto postDto, User user) {
        return postMapper.convertPostDto(postDto, user);
    }

    public List<Post> findPosts(User author) throws UsernameNotFoundException {
        return postRepository.findByAuthor(author);
    }
}

