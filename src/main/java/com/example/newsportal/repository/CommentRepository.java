package com.example.newsportal.repository;

import com.example.newsportal.entity.Comment;
import com.example.newsportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthorComment(User author, long postId);
}
