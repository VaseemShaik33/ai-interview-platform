package com.aiinterview.platform.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aiinterview.platform.entity.RefreshToken;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.exception.InvalidCredentialsException;
import com.aiinterview.platform.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user) {

        // Remove old refresh tokens for this user
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        refreshToken.setExpiresAt(
                LocalDateTime.now().plusDays(7));

        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new InvalidCredentialsException(
                    "Refresh token has been revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException(
                    "Refresh token has expired");
        }

        return refreshToken;
    }

    public void revokeToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid refresh token"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}