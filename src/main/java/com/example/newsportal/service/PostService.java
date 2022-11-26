package com.example.newsportal.service;

import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.dto.PostLikeDto;
import com.example.newsportal.entity.*;
import com.example.newsportal.exception.PostNotFoundException;
import com.example.newsportal.repository.CommentRepository;
import com.example.newsportal.repository.PostRepository;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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

    public Post mapPostDto(PostDto postDto, User user) {
        return postMapper.convertPostDto(postDto, user);
    }

    public List<Post> findPosts(User author) throws UsernameNotFoundException {
        return postRepository.findByAuthor(author);
    }

    public Post addCommentToPost(User author, PostCommentDto postCommentDto) {
        Comment comment = new Comment(LocalDateTime.now(), author, postCommentDto.getText());
        Optional<Post> postById = postRepository.findById(postCommentDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            comment = commentRepository.save(comment);
            post.getComments().add(comment);
            post.setPostRating(post.getPostRating() + 5);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Post addReactionToPost(User author, PostLikeDto postLikeDto) {
        Like like = new Like(LocalDateTime.now(), author, postLikeDto.isLike());
        Optional<Post> postById = postRepository.findById(postLikeDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            post.getLikes().add(like);
            post.setPostRating(post.getPostRating() + 3);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Post removeReactionToPost(User author, PostLikeDto postLikeDto) {
        Optional<Post> postById = postRepository.findById(postLikeDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            for (Like like : post.getLikes()) {
                if (like.getAuthor().getChannelName().equals(author.getChannelName())
                        && like.isLike() == postLikeDto.isLike()) {
                    post.getLikes().remove(like);
                    break;
                }
            }
            post.setPostRating(post.getPostRating() - 3);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Optional<Post> findPostById(Long id) {
        Optional<Post> postById = postRepository.findById(id);
        if (postById.isPresent()) {
            return postById;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Post updatePost(PostDto postDto) {
        Optional<Post> postById = findPostById(postDto.getPostId());
        if (postById.isPresent()) {
            Post editedPost = postById.get();
            editedPost.setHeader(postDto.getHeader());
            editedPost.setDescription(postDto.getDescription());
            editedPost.setImageUrl(postDto.getImageUrl());
            postRepository.save(editedPost);
            return editedPost;
        } else {
            throw new PostNotFoundException();
        }
    }

    public void deletePost(Long id) {
        Optional<Post> postById = findPostById(id);
        if (postById.isPresent()) {
            postRepository.delete(postById.get());
        } else {
            throw new PostNotFoundException();
        }
    }

    public List<Post> showPostsForUserDiscover(User user) {
        Map<Category, Integer> preferences = user.getPreferences();
        List<Category> sortedCategoryPreferences = new ArrayList<>();
        preferences.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .forEach(categoryIntegerEntry -> sortedCategoryPreferences.add(categoryIntegerEntry.getKey()));
        return getTopPostsOfCategory(sortedCategoryPreferences.get(0));
    }

    private List<Post> getTopPostsOfCategory(Category category) {
        List<Post> byCategory = postRepository.findByCategory(category);
        byCategory.sort((o1, o2) -> o2.getPostRating() - o1.getPostRating());
        List<Post> top = new ArrayList<>();
        int size;
        if (byCategory.size() > 10) {
            size = 10;
        } else {
            size = byCategory.size();
        }
        for (int i = 0; i < size; i++) {
            top.add(byCategory.get(i));
        }
        return top;
    }
}