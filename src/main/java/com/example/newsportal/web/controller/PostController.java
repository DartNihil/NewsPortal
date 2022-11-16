package com.example.newsportal.web.controller;

import com.example.newsportal.dto.CommentReplyDto;
import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.repository.UserRepository;
import com.example.newsportal.service.PostService;
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
    @PostMapping("/{postId}/addComment")
    public ResponseEntity<Post> addComment(HttpServletRequest request, @RequestBody PostCommentDto postCommentDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byUsername = userRepository.findByUsername(userDetails.getUsername());
        Post post = postService.addCommentToPost(byUsername.get(), postCommentDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @PostMapping("/{postId}/{commentId}/addReply")
    public ResponseEntity<Post> addCommentReply(HttpServletRequest request, @RequestBody CommentReplyDto commentReplyDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byUsername = userRepository.findByUsername(userDetails.getUsername());
        Post post = postService.addCommentReply(byUsername.get(), commentReplyDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

}
