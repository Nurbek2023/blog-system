package com.nurbek.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;
    private Long authorId;
    private String username;
}

