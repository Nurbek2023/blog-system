package com.nurbek.blog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDto {
    private Long id;

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
