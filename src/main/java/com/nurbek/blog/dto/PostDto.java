package com.nurbek.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Content must not be blank")
    private String content;
    private LocalDateTime createdAt;

    @NotNull(message = "Author ID is required")
    private Long authorId;
    private String authorUsername; // We reference the User by ID
}
