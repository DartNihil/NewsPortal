package com.example.newsportal.dto;

import com.example.newsportal.entity.Post;

public class PostWithReactionsDto {
    private Post post;
    private int likesCount;
    private int dislikesCount;

    public PostWithReactionsDto() {
    }

    public PostWithReactionsDto(Post post, int likesCount, int dislikesCount) {
        this.post = post;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }
}
