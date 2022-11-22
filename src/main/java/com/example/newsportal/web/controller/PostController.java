package com.example.newsportal.web.controller;

import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.dto.PostLikeDto;
import com.example.newsportal.dto.PostWithCommentCountDto;
import com.example.newsportal.dto.PostWithReactionsDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.repository.UserRepository;
import com.example.newsportal.service.PostService;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;
    @PostMapping("/{postId}/addComment")
    public ResponseEntity<PostWithCommentCountDto> addComment(HttpServletRequest request, @RequestBody PostCommentDto postCommentDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byChannelName = userRepository.findByChannelName(userDetails.getUsername());
        Post post = postService.addCommentToPost(byChannelName.get(), postCommentDto);
        PostWithCommentCountDto postWithCommentCountDto = new PostWithCommentCountDto(post, post.getComments().size());
        return new ResponseEntity<>(postWithCommentCountDto, HttpStatus.CREATED);
    }

    @PostMapping("/{postId}/addReaction")
    public ResponseEntity<PostWithReactionsDto> addReaction(HttpServletRequest request, @RequestBody PostLikeDto postLikeDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byChannelName = userRepository.findByChannelName(userDetails.getUsername());
        Post post = postService.addReactionToPost(byChannelName.get(), postLikeDto);
        PostWithReactionsDto postWithReactionsDto = postMapper.convertPostToPostWithReactionsDto(post);
        return new ResponseEntity<>(postWithReactionsDto, HttpStatus.OK);
    }
    @PostMapping("/{postId}/removeReaction")
    public ResponseEntity<PostWithReactionsDto> removeReaction(HttpServletRequest request, @RequestBody PostLikeDto postLikeDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byChannelName = userRepository.findByChannelName(userDetails.getUsername());
        Post post = postService.removeReactionToPost(byChannelName.get(), postLikeDto);
        PostWithReactionsDto postWithReactionsDto = postMapper.convertPostToPostWithReactionsDto(post);
        return new ResponseEntity<>(postWithReactionsDto, HttpStatus.OK);
    }
}