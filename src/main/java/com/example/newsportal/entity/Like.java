package com.example.newsportal.entity;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
public class Like extends AbstractEntity {
    private LocalDateTime likeDateTime;
    @ManyToOne
    private User author;

    private boolean isLike;

    public Like() {
    }

    public Like(LocalDateTime likeDateTime, User author, boolean isLike) {
        this.likeDateTime = likeDateTime;
        this.author = author;
        this.isLike = isLike;
    }

    public LocalDateTime getLikeDateTime() {
        return likeDateTime;
    }

    public void setLikeDateTime(LocalDateTime likeDateTime) {
        this.likeDateTime = likeDateTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
