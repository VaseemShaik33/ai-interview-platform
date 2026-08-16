package com.aiinterview.platform.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}