package com.example.newsportal.dto;

import com.example.newsportal.entity.Post;

public class PostWithCommentCountDto {
    private Post post;
    private int postCommentCount;

    public PostWithCommentCountDto() {
    }

    public PostWithCommentCountDto(Post post, int postCommentCount) {
        this.post = post;
        this.postCommentCount = postCommentCount;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(int postCommentCount) {
        this.postCommentCount = postCommentCount;
    }
}
