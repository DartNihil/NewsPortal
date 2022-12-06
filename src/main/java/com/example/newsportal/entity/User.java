package com.example.newsportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String channelName;
    private String email;
    private String password;
    @ElementCollection
    private Map<Category, Integer> preferences;
    @ManyToMany
    private List<Post> savedPosts;
    @ManyToMany
    private List<User> subscriptions;
    @ManyToMany
    private List<User> subscribers;

    public User(String channelName, String email, String password) {
        this.channelName = channelName;
        this.email = email;
        this.password = password;
    }
}
