package com.jwt.demo.controller;

import com.jwt.demo.jwt.AuthRequest;
import com.jwt.demo.jwt.AuthResponse;
import com.jwt.demo.jwt.JwtService;
import com.jwt.demo.jwt.RefreshTokenRequest;
import com.jwt.demo.model.RefreshToken;
import com.jwt.demo.repository.RefreshTokenRepository;
import com.jwt.demo.service.RefreshTokenService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController

public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }


}
