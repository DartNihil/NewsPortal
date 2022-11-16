package com.example.newsportal.web.controller;

import com.example.newsportal.configuration.jwt.JwtProvider;
import com.example.newsportal.dto.AuthDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.service.PostService;
import com.example.newsportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PostService postService;

    public UserController(UserService userService, JwtProvider jwtProvider, PostService postService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.postService = postService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<String> login(@RequestBody AuthDto authDTO) {
        if (userService.exists(authDTO.getChannelName(), authDTO.getPassword())) {
            String s = jwtProvider.generationToken(authDTO.getChannelName());
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/profile/posting/add")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
        Post post = postService.mapPostDto(postDto);
        Post save = postService.save(post);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

}