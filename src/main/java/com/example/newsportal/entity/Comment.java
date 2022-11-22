package com.example.newsportal.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {
    private LocalDateTime dateTime;
    @ManyToOne
    private User author;
    private String text;
    @OneToMany
    private List<Like> likes;
    @OneToMany
    private List<Comment> comments;

    public Comment() {
    }

    public Comment(LocalDateTime dateTime, User author, String text) {
        this.dateTime = dateTime;
        this.author = author;
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
