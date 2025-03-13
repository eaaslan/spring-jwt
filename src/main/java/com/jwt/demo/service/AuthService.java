package com.jwt.demo.service;


import com.jwt.demo.jwt.AuthRequest;
import com.jwt.demo.jwt.AuthResponse;
import com.jwt.demo.jwt.JwtService;
import com.jwt.demo.model.RefreshToken;
import com.jwt.demo.model.User;
import com.jwt.demo.model.dto.UserDto;
import com.jwt.demo.repository.RefreshTokenRepository;
import com.jwt.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, JwtService jwtService, RefreshTokenRepository refreshTokenRepository,RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
        authenticationProvider.authenticate(authenticationToken);
        Optional< User> user = userRepository.findByUsername(authRequest.username());
        if (user.isPresent()) {
            String jwtToken = jwtService.generateToken(user.get());
            RefreshToken refreshToken =refreshTokenService.generateRefreshToken(user.get());
            refreshTokenRepository.save(refreshToken);
            return new AuthResponse(jwtToken,refreshToken.getRefreshToken());
        }

        }catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
        return null;
    }

    public UserDto register(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        userRepository.save(user);
        return new UserDto(user.getUsername(), user.getPassword());
    }
}
