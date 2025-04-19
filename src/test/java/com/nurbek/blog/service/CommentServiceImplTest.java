package com.nurbek.blog.service;

import com.nurbek.blog.dto.CommentDto;
import com.nurbek.blog.entity.Comment;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import com.nurbek.blog.mapper.CommentMapper;
import com.nurbek.blog.repository.CommentRepository;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceImplTest {

    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        commentMapper = mock(CommentMapper.class);
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        commentService = new CommentServiceImpl(commentRepository, commentMapper, userRepository, postRepository);

    }

    @Test
    void createComment() {
        CommentDto dto = new CommentDto();
        dto.setContent("Test comment");
        dto.setAuthorId(1L);
        dto.setPostId(1L);

        Comment comment = new Comment();
        User user = new User();
        user.setId(1L);
        Post post = new Post();
        post.setId(1L);
        comment.setAuthor(user);
        comment.setPost(post);

        when(commentMapper.toEntity(dto)).thenReturn(comment);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(dto);

        CommentDto result = commentService.createComment(dto);
        assertEquals(dto.getContent(), result.getContent());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getCommentById() {
        Comment comment = new Comment();
        comment.setId(1L);
        CommentDto dto = new CommentDto();
        dto.setId(1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        CommentDto result = commentService.getCommentById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllComments() {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(commentRepository.findAll()).thenReturn(comments);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(new CommentDto());

        List<CommentDto> result = commentService.getAllComments();
        assertEquals(2, result.size());
    }

    @Test
    void updateComment() {
        Comment existing = new Comment();
        existing.setId(1L);
        existing.setContent("Old content");

        CommentDto updatedDto = new CommentDto();
        updatedDto.setContent("Updated content");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(commentRepository.save(existing)).thenReturn(existing);
        when(commentMapper.toDto(existing)).thenReturn(updatedDto);

        CommentDto result = commentService.updateComment(1L, updatedDto);
        assertEquals("Updated content", result.getContent());
    }

    @Test
    void deleteComment() {
        commentService.deleteComment(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }
}