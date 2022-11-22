package com.example.newsportal.dto;

public class PostLikeDto {
    private long postId;
    private boolean isLike;

    public PostLikeDto() {
    }

    public PostLikeDto(long postId, boolean isLike) {
        this.postId = postId;
        this.isLike = isLike;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
