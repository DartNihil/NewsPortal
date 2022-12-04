package com.example.newsportal.dto;

import com.example.newsportal.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class PostCommentDto {
    @NotNull
    private long postId;
    @NotBlank(message = "Field cant be empty!")
    private String text;

    public PostCommentDto() {
    }

    public PostCommentDto(long postId, String text) {
        this.postId = postId;
        this.text = text;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
