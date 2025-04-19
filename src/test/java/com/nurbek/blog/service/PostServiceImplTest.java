package com.nurbek.blog.service;

import com.nurbek.blog.mapper.PostMapper;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.nurbek.blog.dto.PostDto;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;

import jakarta.persistence.EntityNotFoundException;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

class PostServiceImplTest {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostMapper postMapper;
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        userRepository = mock(UserRepository.class);
        postMapper = mock(PostMapper.class);
        postService = new PostServiceImpl(postRepository, userRepository, postMapper);
    }

    @Test
    void createPost() {
        PostDto inputDto = new PostDto();
        inputDto.setTitle("Test Title");
        inputDto.setContent("Test Content");
        inputDto.setAuthorId(1L);

        User fakeAuthor = new User();
        fakeAuthor.setId(1L);

        Post postEntity = new Post();
        postEntity.setAuthor(fakeAuthor);

        Post savedPost = new Post();
        savedPost.setId(10L);
        savedPost.setTitle("Test Title");
        savedPost.setContent("Test Content");
        savedPost.setCreatedAt(LocalDateTime.now());
        savedPost.setAuthor(fakeAuthor);

        PostDto outputDto = new PostDto();
        outputDto.setId(10L);
        outputDto.setTitle("Test Title");

        when(postMapper.toEntity(inputDto)).thenReturn(postEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.of(fakeAuthor));
        when(postRepository.save(postEntity)).thenReturn(savedPost);
        when(postMapper.toDto(savedPost)).thenReturn(outputDto);

        PostDto result = postService.createPost(inputDto);

        assertEquals(outputDto.getId(), result.getId());
        assertEquals(outputDto.getTitle(), result.getTitle());
        verify(postRepository).save(postEntity);
    }

    @Test
    void getPostById() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.getPostById(99L));

    }

    @Test
    void getPostById_ShouldReturnPostDto_WhenFound() {
        Post post = new Post();
        post.setId(1L);

        PostDto postDto = new PostDto();
        postDto.setId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto result = postService.getPostById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getAllPosts_ShouldReturnListOfPostDtos_WhenFound() {
        Post post1 = new Post();
        Post post2 = new Post();

        PostDto dto1 = new PostDto();
        dto1.setId(1L);
        PostDto dto2 = new PostDto();
        dto2.setId(2L);

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));
        when(postMapper.toDto(post1)).thenReturn(dto1);
        when(postMapper.toDto(post2)).thenReturn(dto2);

        List<PostDto> result = postService.getAllPosts();

        assertEquals(2, result.size());
    }

    @Test
    void updatePost() {
        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("Old Title");

        PostDto updatedDto = new PostDto();
        updatedDto.setTitle("New Title");
        updatedDto.setContent("Updated content");

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle("New Title");
        updatedPost.setContent("Updated content");

        PostDto resultDto = new PostDto();
        resultDto.setId(1L);
        resultDto.setTitle("New Title");

        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(updatedPost);
        when(postMapper.toDto(updatedPost)).thenReturn(resultDto);

        PostDto result = postService.updatePost(1L, updatedDto);

        assertEquals("New Title", result.getTitle());
        verify(postRepository).save(existingPost);
    }

    @Test
    void deletePost() {
        postService.deletePost(1L);
        verify(postRepository).deleteById(1L);

    }
}