package com.example.newsportal.service;

import com.example.newsportal.dto.CommentDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Comment;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.exception.PostNotFoundException;
import com.example.newsportal.repository.CommentRepository;
import com.example.newsportal.repository.PostRepository;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.commentRepository = commentRepository;
    }

    public Post save(Post post) {
        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    public Post mapPostDto(PostDto postDto) {
        return postMapper.convertPostDto(postDto);
    }

    public Post addCommentToPost(User author, CommentDto commentDto) {
        Comment comment = new Comment(LocalDateTime.now(), author, commentDto.getText());
        Optional<Post> postById = postRepository.findById(commentDto.getId());
        if (postById.isPresent()) {
            Post post = postById.get();
            comment = commentRepository.save(comment);
            post.getComments().add(comment);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }
}
