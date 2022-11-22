package com.example.newsportal.dto;

import javax.validation.constraints.NotBlank;

public class PostDto {
    @NotBlank(message = "Field cant be empty!")
    private String header;
    @NotBlank(message = "Field cant be empty!")
    private String description;

    private String imageUrl;
    private Long postId;

    public PostDto() {
    }

    public PostDto(String header, String description, String imageUrl, Long postId) {
        this.header = header;
        this.description = description;
        this.imageUrl = imageUrl;
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
