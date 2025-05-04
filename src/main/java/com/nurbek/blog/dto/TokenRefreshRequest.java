package com.nurbek.blog.dto;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
