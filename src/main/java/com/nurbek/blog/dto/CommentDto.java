package com.nurbek.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;

    @NotBlank(message = "Comment content must not be blank")
    private String content;
    private LocalDateTime createdAt;

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotNull(message = "Author ID is required")
    private Long authorId;
    private String username;
}

