package com.example.newsportal.dto;

public class PostCommentDto {
    private long postId;
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
