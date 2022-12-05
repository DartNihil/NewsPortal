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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

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
            return ok(s);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/profile/posting/add")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        Post post = postService.composePostInfo(postDto, u);
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
        return ok(posts);
    }

    @GetMapping("/openPost/{postId}")
    public ResponseEntity<?> openPost(@PathVariable("postId") long id, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        Post post = postService.findPostById(id);
        userService.ratePreferences(u, post.getCategory());
        return new ResponseEntity<>(post, HttpStatus.OK);
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

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        User saveUser = userService.save(user);
        return ok(saveUser);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAllUsers();
        return ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") Long userId) {
        User userById = userService.findUserById(userId);
        return ok(userById);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}