package com.example.newsportal.dto;

public class PostDto {
    private String header;
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
