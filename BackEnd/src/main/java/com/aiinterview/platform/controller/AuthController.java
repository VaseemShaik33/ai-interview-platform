package com.aiinterview.platform.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aiinterview.platform.dto.ForgotPasswordRequest;
import com.aiinterview.platform.dto.LoginRequest;
import com.aiinterview.platform.dto.LoginResponse;
import com.aiinterview.platform.dto.RefreshTokenRequest;
import com.aiinterview.platform.dto.RefreshTokenResponse;
import com.aiinterview.platform.dto.RegisterRequest;
import com.aiinterview.platform.dto.RegisterResponse;
import com.aiinterview.platform.dto.ResetPasswordRequest;
import com.aiinterview.platform.entity.RefreshToken;
import com.aiinterview.platform.service.AuthService;
import com.aiinterview.platform.service.JwtService;
import com.aiinterview.platform.service.PasswordResetService;
import com.aiinterview.platform.service.RefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final PasswordResetService passwordResetService;

    public AuthController(
            AuthService authService,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            PasswordResetService passwordResetService) {

        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody @Valid RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @RequestBody @Valid RefreshTokenRequest request) {

        // Validate old refresh token
        RefreshToken oldRefreshToken = refreshTokenService.validateRefreshToken(
                request.refreshToken());

        // Revoke old refresh token
        refreshTokenService.revokeToken(
                request.refreshToken());

        // Get user
        var user = oldRefreshToken.getUser();

        // Generate new access token
        String newAccessToken = jwtService.generateAccessToken(user);

        // Generate new refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new RefreshTokenResponse(
                        newAccessToken,
                        newRefreshToken.getToken()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequest request) {

        passwordResetService.forgotPassword(request.email());

        return ResponseEntity.ok(
                "If an account with that email exists, a reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request) {

        passwordResetService.resetPassword(
                request.token(),
                request.newPassword());

        return ResponseEntity.ok("Password has been reset successfully.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody @Valid RefreshTokenRequest request) {

        refreshTokenService.revokeToken(
                request.refreshToken());

        return ResponseEntity.ok(
                "Logout successful");
    }
}