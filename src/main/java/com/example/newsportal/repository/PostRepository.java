package com.example.newsportal.repository;

import com.example.newsportal.entity.Category;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post , Long> {
    List<Post> findByAuthor(User author);
    List<Post> findByCategory(Category category);
}
