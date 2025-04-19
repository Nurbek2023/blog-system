package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.UserDto;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;
class UserMapperTest {

    private final UserMapper userMapper = Mappers
            .getMapper(UserMapper.class);

    @Test
    void toDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("nurbek");
        user.setEmail("nurbek@example.com");

        UserDto dto = userMapper.toDto(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());

    }

    @Test
    void toEntity() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("nurbek");
        dto.setEmail("nurbek@example.com");

        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
    }
}