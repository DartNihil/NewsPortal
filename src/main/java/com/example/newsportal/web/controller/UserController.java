package com.example.newsportal.web.controller;

import com.example.newsportal.configuration.jwt.JwtProvider;
import com.example.newsportal.dto.AuthDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import com.example.newsportal.service.PostService;
import com.example.newsportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto, HttpServletRequest request) {
        User u = (User) request.getAttribute("userDetails");
        Post post = postService.mapPostDto(postDto, u);
        Post save = postService.save(post);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/{channelName}/posts")
    public ResponseEntity<List<Post>> channelName(@PathVariable("channelName") String channelName) {
        Optional<User> user = userService.findUserByChannelName(channelName);
        List<Post> posts;
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            posts = postService.findPosts(user.get());
        }
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/profile/posting/{postId}")
    public ResponseEntity<Post> update(@PathVariable("postId") Long postId,
                                       @RequestBody PostDto postDto) {
        postDto.setPostId(postId);
        Post post = postService.updatePost(postDto);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/profile/posting/{postId}")
    public ResponseEntity<Post> delete(@PathVariable("postId") Long postId) {
        postService.findPostById(postId);
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/discover")
    public ResponseEntity<List<Post>> showDiscoverPosts(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Optional<User> byChannelName = userService.findUserByChannelName(user.getChannelName());
        List<Post> posts = postService.showPostsForUserDiscover(byChannelName.get());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @PostMapping("/saved")
    public ResponseEntity<List<Post>> showSavedPosts(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Optional<User> byChannelName = userService.findUserByChannelName(user.getChannelName());
        List<Post> savedPosts = byChannelName.get().getSavedPosts();
        return new ResponseEntity<>(savedPosts, HttpStatus.OK);
    }
}