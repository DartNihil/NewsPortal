package com.example.newsportal.web.controller;

import com.example.newsportal.dto.CommentDto;
import com.example.newsportal.entity.Comment;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.repository.UserRepository;
import com.example.newsportal.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
    @PostMapping("/{id}/addComment")
    public ResponseEntity<Post> addComment(HttpServletRequest request, @RequestBody CommentDto commentDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> byUsername = userRepository.findByUsername(userDetails.getUsername());
        postService.addCommentToPost(byUsername.get(), commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
