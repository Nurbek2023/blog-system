package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
