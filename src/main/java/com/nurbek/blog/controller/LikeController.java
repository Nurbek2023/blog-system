package com.nurbek.blog.controller;

import com.nurbek.blog.dto.LikeDto;
import com.nurbek.blog.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // Like a post
    @Operation(summary = "Like a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid like request")
    })
    @PostMapping
    public ResponseEntity<LikeDto> likePost(@Valid @RequestBody LikeDto likeDto) {
        return ResponseEntity.ok(likeService.likePost(likeDto));
    }

    // Unlike a post
    @Operation(summary = "Unlike a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post unliked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid unlike request")
    })
    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestParam Long postId, @RequestParam Long userId) {
        likeService.unlikePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    // Check if a post is liked by a user
    @Operation(summary = "Check if a post is liked by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boolean result returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isPostLiked(
            @RequestParam Long postId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(likeService.isPostLikedByUser(postId, userId));
    }

    // Get all likes for a post
    @Operation(summary = "Get all likes for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikesForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getAllLikesForPost(postId));
    }
}
