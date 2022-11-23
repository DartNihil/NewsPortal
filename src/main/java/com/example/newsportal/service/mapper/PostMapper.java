package com.example.newsportal.service.mapper;

import com.example.newsportal.dto.PostDto;
import com.example.newsportal.dto.PostWithReactionsDto;
import com.example.newsportal.entity.Like;
import com.example.newsportal.entity.Post;
import com.example.newsportal.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PostMapper {
    public Post convertPostDto(PostDto postDto, User user) {
        return new Post(postDto.getHeader(), postDto.getDescription(), postDto.getImageUrl(), user);
    }

    public PostWithReactionsDto convertPostToPostWithReactionsDto(Post post) {
        List<Like> likes = post.getLikes();
        int likesCount = 0;
        int dislikesCount = 0;
        for (Like like : likes) {
            if (like.isLike()) {
                likesCount++;
            } else {
                dislikesCount++;
            }
        }
        return new PostWithReactionsDto(post, likesCount, dislikesCount);
    }
}
