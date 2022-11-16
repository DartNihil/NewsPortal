package com.example.newsportal.web.controller;

import com.example.newsportal.dto.CommentDto;
import com.example.newsportal.entity.Comment;
import com.example.newsportal.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @PostMapping("/{id}/addComment")
    public ResponseEntity<Post> addComment(@RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
