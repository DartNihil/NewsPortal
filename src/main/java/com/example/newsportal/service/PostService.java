package com.example.newsportal.service;

import com.example.newsportal.dto.PostCommentDto;
import com.example.newsportal.dto.PostDto;
import com.example.newsportal.dto.PostLikeDto;
import com.example.newsportal.entity.*;
import com.example.newsportal.exception.PostNotFoundException;
import com.example.newsportal.repository.CategoryWordsStorage;
import com.example.newsportal.repository.CommentRepository;
import com.example.newsportal.repository.PostRepository;
import com.example.newsportal.service.mapper.PostMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.commentRepository = commentRepository;
    }

    public Post save(Post post) {
        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    public Post composePostInfo(PostDto postDto, User user) {
        Post post = postMapper.convertPostDto(postDto, user);
        post.setCategory(defineCategory(postDto));
        return post;
    }

    public List<Post> findPosts(User author) throws UsernameNotFoundException {
        return postRepository.findByAuthor(author);
    }

    public Post addCommentToPost(User author, PostCommentDto postCommentDto) {
        Comment comment = new Comment(LocalDateTime.now(), author, postCommentDto.getText());
        Optional<Post> postById = postRepository.findById(postCommentDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            comment = commentRepository.save(comment);
            post.getComments().add(comment);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Post addReactionToPost(User author, PostLikeDto postLikeDto) {
        Like like = new Like(LocalDateTime.now(), author, postLikeDto.isLike());
        Optional<Post> postById = postRepository.findById(postLikeDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            post.getLikes().add(like);
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    public Post removeReactionToPost(User author, PostLikeDto postLikeDto) {
        Optional<Post> postById = postRepository.findById(postLikeDto.getPostId());
        if (postById.isPresent()) {
            Post post = postById.get();
            for (Like like : post.getLikes()) {
                if (like.getAuthor().getChannelName().equals(author.getChannelName())
                        && like.isLike() == postLikeDto.isLike()) {
                    post.getLikes().remove(like);
                    break;
                }
            }
            post = postRepository.save(post);
            return post;
        } else {
            throw new PostNotFoundException();
        }
    }

    private Category defineCategory(PostDto postDto) {
        Map<Category, Integer> categoryRatings = analyzeCategoryRatings(postDto);
        return categoryRatings
                .keySet()
                .stream()
                .max(Comparator.comparing(categoryRatings::get))
                .orElse(Category.UNDEFINED);
    }

    private List<String> fragmentText(PostDto postDto) {
        List<String> fullText = new ArrayList<>();
        Collections.addAll(fullText, postDto.getHeader().split("[\\W_]+"));
        Collections.addAll(fullText, postDto.getDescription().split("[\\W_]+"));
        return fullText;
    }

    private Map<Category, Integer> analyzeCategoryRatings(PostDto postDto) {
        Map<Category, Integer> wordCountByCategory = new HashMap<>();
        int musicWordsCount = 0;
        int theatreWordsCount = 0;
        int cinemaWordsCount = 0;
        for (String s : fragmentText(postDto)) {
            for (int j = 0; j < CategoryWordsStorage.categoryWordsSize; j++) {
                if (j < CategoryWordsStorage.MUSIC.size() && s.equalsIgnoreCase(CategoryWordsStorage.MUSIC.get(j))) {
                    wordCountByCategory.put(Category.MUSIC, musicWordsCount++);
                }
                if (j < CategoryWordsStorage.THEATRE.size() && s.equalsIgnoreCase(CategoryWordsStorage.THEATRE.get(j))) {
                    wordCountByCategory.put(Category.THEATRE, theatreWordsCount++);
                }
                if (j < CategoryWordsStorage.CINEMA.size() && s.equalsIgnoreCase(CategoryWordsStorage.CINEMA.get(j))) {
                    wordCountByCategory.put(Category.CINEMA, cinemaWordsCount++);
                }
            }
        }
        return wordCountByCategory;
    }
}

