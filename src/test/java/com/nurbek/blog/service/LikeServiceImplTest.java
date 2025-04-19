package com.nurbek.blog.service;

import com.nurbek.blog.dto.LikeDto;
import com.nurbek.blog.entity.Like;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import com.nurbek.blog.mapper.LikeMapper;
import com.nurbek.blog.repository.LikeRepository;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceImplTest {

    private LikeRepository likeRepository;
    private LikeMapper likeMapper;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private LikeServiceImpl likeService;

    @BeforeEach
    void setUp() {
        likeRepository = mock(LikeRepository.class);
        likeMapper = mock(LikeMapper.class);
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        likeService = new LikeServiceImpl(likeRepository, likeMapper, userRepository, postRepository);

    }

    @Test
    void likePost() {
        LikeDto dto = new LikeDto();
        dto.setPostId(1L);
        dto.setUserId(2L);

        Like like = new Like();
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(2L);
        like.setPost(post);
        like.setUser(user);

        when(likeRepository.existsByPostIdAndUserId(1L, 2L)).thenReturn(false);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(likeMapper.toEntity(dto)).thenReturn(like);
        when(likeRepository.save(like)).thenReturn(like);
        when(likeMapper.toDto(like)).thenReturn(dto);

        LikeDto result = likeService.likePost(dto);
        assertEquals(dto.getUserId(), result.getUserId());
        verify(likeRepository).save(like);
    }

    @Test
    void unlikePost() {
        likeService.unlikePost(1L, 2L);
        verify(likeRepository, times(1)).deleteByPostIdAndUserId(1L, 2L);
    }

    @Test
    void isPostLikedByUser() {
        when(likeRepository.existsByPostIdAndUserId(1L, 2L)).thenReturn(true);
        assertTrue(likeService.isPostLikedByUser(1L, 2L));
    }

    @Test
    void getAllLikesForPost() {
        when(likeRepository.findByPostId(1L)).thenReturn(List.of(new Like(), new Like()));
        when(likeMapper.toDto(any())).thenReturn(new LikeDto());

        List<LikeDto> result = likeService.getAllLikesForPost(1L);
        assertEquals(2, result.size());
    }
}