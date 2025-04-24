package com.nurbek.blog.service;

import com.nurbek.blog.dto.LikeDto;
import com.nurbek.blog.entity.Like;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import com.nurbek.blog.mapper.LikeMapper;
import com.nurbek.blog.repository.LikeRepository;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public LikeDto likePost(LikeDto likeDto) {
        if (likeRepository.existsByPostIdAndUserId(likeDto.getPostId(), likeDto.getUserId())) {
            throw new IllegalStateException("Already liked this post");
        }

        Like like = likeMapper.toEntity(likeDto);
        like.setPost(postRepository.findById(likeDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found")));
        like.setUser(userRepository.findById(likeDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));

        return likeMapper.toDto(likeRepository.save(like));
    }

    @Override
    @Transactional
    public void unlikePost(Long postId, Long userId) {
//        if (!likeRepository.existsByPostIdAndUserId(postId, userId)) {
//            throw new EntityNotFoundException("Like not found for postId=" + postId + " and userId=" + userId);
//        }
        likeRepository.deleteByPostIdAndUserId(postId, userId);
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public List<LikeDto> getAllLikesForPost(Long postId) {
        return likeRepository.findByPostId(postId).stream()
                .map(likeMapper::toDto)
                .collect(Collectors.toList());
    }
}
