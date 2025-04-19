package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.PostDto;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    void toDto() {
        User author = new User();
        author.setId(1L);
        author.setUsername("john_doe");

        Post post = new Post();
        post.setId(10L);
        post.setTitle("My First Post");
        post.setContent("This is the content.");
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(author);

        PostDto dto = postMapper.toDto(post);

        assertEquals(post.getId(), dto.getId());
        assertEquals(post.getTitle(), dto.getTitle());
        assertEquals(post.getContent(), dto.getContent());
        assertEquals(post.getCreatedAt(), dto.getCreatedAt());
        assertEquals(post.getAuthor().getId(), dto.getAuthorId());
        // Note: authorUsername is not mapped — optional/custom handling if needed

    }

    @Test
    void toEntity() {
        PostDto dto = new PostDto();
        dto.setId(10L);
        dto.setTitle("My First Post");
        dto.setContent("This is the content.");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setAuthorId(1L);
        dto.setAuthorUsername("john_doe"); // Will be ignored in mapping

        Post post = postMapper.toEntity(dto);

        assertEquals(dto.getId(), post.getId());
        assertEquals(dto.getTitle(), post.getTitle());
        assertEquals(dto.getContent(), post.getContent());
        assertEquals(dto.getCreatedAt(), post.getCreatedAt());
        assertEquals(dto.getAuthorId(), post.getAuthor().getId());

    }
}