package com.jwt.demo.service;

import com.jwt.demo.jwt.AuthResponse;
import com.jwt.demo.jwt.JwtService;
import com.jwt.demo.jwt.RefreshTokenRequest;
import com.jwt.demo.model.RefreshToken;
import com.jwt.demo.model.User;
import com.jwt.demo.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService
{

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4)); // 1 day expiry
        refreshToken.setUser(user);
        return refreshToken;
    }

    private boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().before(new java.util.Date());
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();
        Optional<RefreshToken> token = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (token.isPresent()) {
            RefreshToken refreshTokenEntity = token.get();
            if (isTokenExpired(refreshTokenEntity)) {
                throw new RuntimeException("Refresh token expired");
            }
            User user = refreshTokenEntity.getUser();
            String jwtToken = jwtService.generateToken(user);
            RefreshToken refreshToken1 =generateRefreshToken(user);
            refreshTokenRepository.save(refreshToken1);
            return new AuthResponse(jwtToken, refreshToken1.getRefreshToken());
        } else {
            throw new RuntimeException("Invalid refresh token");
        }

    }
}
