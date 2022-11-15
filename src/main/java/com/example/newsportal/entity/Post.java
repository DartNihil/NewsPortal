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
    @ManyToMany
    private List<Comment> comments;
    @ManyToMany
    private List<Like> likes;

    public Post() {
    }

    public Post(String header, String description, String photoOrVideoUrl, LocalDateTime creationTime, List<Comment> comments, List<Like> likes) {
        this.header = header;
        this.description = description;
        this.photoOrVideoUrl = photoOrVideoUrl;
        this.creationTime = creationTime;
        this.comments = comments;
        this.likes = likes;
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
