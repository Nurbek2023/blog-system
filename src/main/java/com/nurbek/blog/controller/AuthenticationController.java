package com.nurbek.blog.controller;

import com.nurbek.blog.dto.*;
import com.nurbek.blog.entity.RefreshToken;
import com.nurbek.blog.entity.User;
import com.nurbek.blog.exception.TokenNotFoundException;
import com.nurbek.blog.mapper.UserMapper;
import com.nurbek.blog.service.AuthenticationService;
import com.nurbek.blog.service.JwtService;
import com.nurbek.blog.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthenticationController(JwtService jwtService,
                                    RefreshTokenService refreshTokenService,
                                    AuthenticationService authenticationService,
                                    UserMapper userMapper
    ) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(userMapper.toDto(registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        RefreshToken refreshToken = refreshTokenService.findByUser(authenticatedUser)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token not found"));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRefreshToken(refreshToken.getToken());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(AuthenticationResponse.builder()
                            .accessToken(token)
                            .refreshToken(requestRefreshToken)
                            .build());
                })
                .orElseThrow(() -> new TokenNotFoundException("Refresh token is not in database!"));
    }
}
