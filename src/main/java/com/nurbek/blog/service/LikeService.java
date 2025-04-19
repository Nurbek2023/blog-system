package com.nurbek.blog.service;

import com.nurbek.blog.dto.LikeDto;

import java.util.List;

public interface LikeService {
    LikeDto likePost(LikeDto likeDto);
    void unlikePost(Long postId, Long userId);
    boolean isPostLikedByUser(Long postId, Long userId);
    List<LikeDto> getAllLikesForPost(Long postId);
}
