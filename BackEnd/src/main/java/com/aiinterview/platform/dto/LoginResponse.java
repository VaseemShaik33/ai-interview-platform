package com.aiinterview.platform.dto;

public record LoginResponse(
        String message,
        Long userId,
        String name,
        String email,
        String role,
        String accessToken,
        String refreshToken
) {}
