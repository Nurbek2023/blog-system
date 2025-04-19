package com.nurbek.blog.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;// Keep this for internal use (e.g., author references)
    private String username;
    private String email;
}
