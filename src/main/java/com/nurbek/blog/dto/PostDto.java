package com.nurbek.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorUsername; // We reference the User by ID
}
