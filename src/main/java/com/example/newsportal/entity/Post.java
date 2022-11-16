package com.example.newsportal.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends AbstractEntity {
    private String header;
    private String description;
    private String photoOrVideoUrl;
    private LocalDateTime creationTime;
    @ManyToOne
    private User author;
    @OneToMany
    private List<Comment> comments;
    @ManyToMany
    private List<Like> likes;

    public Post() {
    }

    public Post(String header, String description, String photoOrVideoUrl, User author) {
        this.header = header;
        this.description = description;
        this.photoOrVideoUrl = photoOrVideoUrl;
        this.author = author;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoOrVideoUrl() {
        return photoOrVideoUrl;
    }

    public void setPhotoOrVideoUrl(String photoOrVideoUrl) {
        this.photoOrVideoUrl = photoOrVideoUrl;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
