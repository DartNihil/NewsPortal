package com.example.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractEntity {
    @ManyToOne
    private User author;
    private String header;
    private String description;
    private String imageUrl;
    private LocalDateTime creationTime;
    @OneToMany
    private List<Comment> comments;
    @ManyToMany
    private List<Like> likes;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int postRating;

    public Post(String header, String description, String imageUrl, User author) {
        this.header = header;
        this.description = description;
        this.imageUrl = imageUrl;
        this.author = author;
    }
}
