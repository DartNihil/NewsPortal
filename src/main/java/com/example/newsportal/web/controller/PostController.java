package com.example.newsportal.web.controller;

import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.dto.PostWithCommentCountDto;
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
    public ResponseEntity<PostWithCommentCountDto> addComment(HttpServletRequest request, @RequestBody PostCommentDto postCommentDto) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        Optional<User> ByChannelName = userRepository.findByChannelName(userDetails.getUsername());
        Post post = postService.addCommentToPost(ByChannelName.get(), postCommentDto);
        PostWithCommentCountDto postWithCommentCountDto = new PostWithCommentCountDto(post, post.getComments().size());
        return new ResponseEntity<>(postWithCommentCountDto, HttpStatus.CREATED);
    }
}
