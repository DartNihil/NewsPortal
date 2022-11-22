package com.example.newsportal.service;

import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.dto.PostLikeDto;
import com.example.newsportal.entity.Comment;
import com.example.newsportal.entity.Like;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.exception.PostNotFoundException;
import com.example.newsportal.repository.CommentRepository;
import com.example.newsportal.repository.PostRepository;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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


    public Post mapPostDto(PostDto postDto , User user) {
        return postMapper.convertPostDto(postDto , user);
    }

    public Post addCommentToPost(User author, PostCommentDto postCommentDto) {
        Comment comment = new Comment(LocalDateTime.now(), author, postCommentDto.getText());
        Optional<Post> postById = postRepository.findById(postCommentDto.getPostId());
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

    public Post addReactionToPost(User author, PostLikeDto postLikeDto) {
        Like like = new Like(LocalDateTime.now(), author, postLikeDto.isLike());
        Optional<Post> postById = postRepository.findById(postLikeDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            post.getLikes().add(like);
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
            for (Like like:post.getLikes()) {
                if(like.getAuthor().getChannelName().equals(author.getChannelName())
                        && like.isLike() == postLikeDto.isLike()) {
                    post.getLikes().remove(like);
                    break;
                }
            }
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

}
