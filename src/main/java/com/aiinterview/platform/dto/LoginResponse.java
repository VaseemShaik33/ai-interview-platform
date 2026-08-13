package com.aiinterview.platform.dto;

public record LoginResponse(
    String message,
    Long userId,
    String token

) {
    
}
