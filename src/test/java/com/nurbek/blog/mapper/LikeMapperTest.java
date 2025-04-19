package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.LikeDto;
import com.nurbek.blog.entity.Like;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class LikeMapperTest {

    private final LikeMapper likeMapper = Mappers.getMapper(LikeMapper.class);


    @Test
    void toDto() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(10L);

        Like like = new Like();
        like.setId(100L);
        like.setUser(user);
        like.setPost(post);

        LikeDto dto = likeMapper.toDto(like);

        assertEquals(like.getId(), dto.getId());
        assertEquals(like.getUser().getId(), dto.getUserId());
        assertEquals(like.getPost().getId(), dto.getPostId());

    }

    @Test
    void toEntity() {
        LikeDto dto = new LikeDto();
        dto.setId(100L);
        dto.setUserId(1L);
        dto.setPostId(10L);

        Like like = likeMapper.toEntity(dto);

        assertEquals(dto.getId(), like.getId());
        assertEquals(dto.getUserId(), like.getUser().getId());
        assertEquals(dto.getPostId(), like.getPost().getId());
    }
}