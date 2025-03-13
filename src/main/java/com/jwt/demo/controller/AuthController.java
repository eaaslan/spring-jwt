package com.jwt.demo.controller;


import com.jwt.demo.jwt.AuthRequest;
import com.jwt.demo.jwt.AuthResponse;
import com.jwt.demo.jwt.RefreshTokenRequest;
import com.jwt.demo.model.dto.UserDto;
import com.jwt.demo.service.AuthService;
import com.jwt.demo.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;

    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody  AuthRequest authRequest) {
       return authService.register(authRequest);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse= refreshTokenService.refreshToken(refreshTokenRequest);
        return authResponse;
    }
}
