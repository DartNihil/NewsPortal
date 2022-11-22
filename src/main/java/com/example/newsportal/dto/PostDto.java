package com.example.newsportal.dto;

import javax.validation.constraints.NotBlank;

public class PostDto {
    @NotBlank(message = "Field cant be empty!")
    private String header;
    @NotBlank(message = "Field cant be empty!")
    private String description;
    private String photoOrVideoUrl;

    public PostDto() {
    }

    public PostDto(String header, String description, String photoOrVideoUrl) {
        this.header = header;
        this.description = description;
        this.photoOrVideoUrl = photoOrVideoUrl;
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
}
