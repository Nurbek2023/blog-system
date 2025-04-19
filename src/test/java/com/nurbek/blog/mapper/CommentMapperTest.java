package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.CommentDto;
import com.nurbek.blog.entity.Comment;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void toDto() {
        User author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        Post post = new Post();
        post.setId(10L);

        Comment comment = new Comment();
        comment.setId(100L);
        comment.setContent("Nice post!");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(author);
        comment.setPost(post);

        CommentDto dto = commentMapper.toDto(comment);

        assertEquals(comment.getId(), dto.getId());
        assertEquals(comment.getContent(), dto.getContent());
        assertEquals(comment.getAuthor().getId(), dto.getAuthorId());
        assertEquals(comment.getPost().getId(), dto.getPostId());
        assertEquals(comment.getAuthor().getUsername(), dto.getUsername());

    }

    @Test
    void toEntity() {
        CommentDto dto = new CommentDto();
        dto.setId(100L);
        dto.setContent("Nice post!");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setAuthorId(1L);
        dto.setPostId(10L);

        Comment comment = commentMapper.toEntity(dto);

        assertEquals(dto.getId(), comment.getId());
        assertEquals(dto.getContent(), comment.getContent());
        assertEquals(dto.getAuthorId(), comment.getAuthor().getId());
        assertEquals(dto.getPostId(), comment.getPost().getId());

    }
}